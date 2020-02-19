package com.sunmengjie.cms.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.sunmengjie.cms.common.CmsContant;
import com.sunmengjie.cms.entity.Articles;
import com.sunmengjie.cms.entity.Category;
import com.sunmengjie.cms.entity.Channel;
import com.sunmengjie.cms.entity.Slide;
import com.sunmengjie.cms.mapper.ArticleRep;
import com.sunmengjie.cms.service.ArticlesService;
import com.sunmengjie.cms.utils.HLUtils;

@Controller
public class IndexController {

	@Autowired
	private ArticlesService articlesService;

	@Autowired
	RedisTemplate redisTemplate;

	@Autowired // 注入es仓库
	ArticleRep articleRep;

	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;

	// es高亮查询
	@GetMapping("index")
	public String search(String key, Model model, @RequestParam(defaultValue = "1") int pageNum,
			HttpServletRequest request) throws Exception {

		// 获取所有的栏目
		Thread t1 = new Thread() {
			@Override
			public void run() {

				List<Channel> channels = articlesService.getChannels();
				request.setAttribute("channels", channels);
			};
		};
		// 获取最新文章
		Thread t3 = new Thread() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				// redis作为缓存来优化最新文章
				// 1.从Redis中查询最新文章
				List<Articles> redisArticles = redisTemplate.opsForList().range("new_articles", 0, -1);
				// 2.判断Redis中查询的文章是否为空
				if (redisArticles == null || redisArticles.size() == 0) {
					// 3.如果为空,从mysql中查询最新文章 ，放入redis中
					System.err.println("从mysql中查询最新的文章*******");
					List<Articles> lastArticles = articlesService.lastList();

					// 放入redis中
					redisTemplate.opsForList().leftPushAll("new_articles", lastArticles.toArray());
					
					//Redis缓存功能，有效期5分钟
					redisTemplate.expire("new_articles", 5, TimeUnit.MINUTES);

					// 返回给前台
					request.setAttribute("lastArticles", lastArticles);
				} else {
					// 4.如果不为空直接返回到前台
					System.err.println("从redis中查询最新的文章-------");
					request.setAttribute("lastArticles", redisArticles);
				}

			};
		};
		// 轮播图
		Thread t4 = new Thread() {
			@Override
			public void run() {

				List<Slide> slides = articlesService.getSlides();
				request.setAttribute("slides", slides);
			};
		};

		t1.start();
		t3.start();
		t4.start();
		
		t1.join();
		t3.join();
		t4.join();
		//利用es的仓库来查询（无高亮）
//		List<Articles> list = articleRep.findByTitle(key);
//		PageInfo<Articles> pageInfo = new PageInfo<>(list);
//		model.addAttribute("articlesPage", pageInfo);
//		model.addAttribute("key", key);
		//定义一个开始时间
		long start = System.currentTimeMillis();
		PageInfo<Articles> pageInfo = (PageInfo<Articles>) HLUtils.findByHighLight(elasticsearchTemplate, Articles.class, pageNum, CmsContant.PAGE_SIZE, new String[] {"title"}, "id", key);
		//定义一个结束时间
		long end = System.currentTimeMillis();
		System.out.println("es查询一共永用了"+(end-start)+"毫秒");
		model.addAttribute("articlesPage", pageInfo);
		model.addAttribute("key", key);
		return "index";
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = { "index", "/" })
	public String index(HttpServletRequest request, @RequestParam(defaultValue = "1") int pageNum)
			throws InterruptedException {
		// 获取所有的栏目
		Thread t1 = new Thread() {
			@Override
			public void run() {

				List<Channel> channels = articlesService.getChannels();
				request.setAttribute("channels", channels);
			};
		};

		// 获取热门文章
		Thread t2 = new Thread() {
			@Override
			public void run() {
				PageInfo<Articles> articlesPage = articlesService.hotList(pageNum);
				request.setAttribute("articlesPage", articlesPage);
			};
		};

		// 获取最新文章
		Thread t3 = new Thread() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				// redis作为缓存来优化最新文章
				// 1.从Redis中查询最新文章
				List<Articles> redisArticles = redisTemplate.opsForList().range("new_articles", 0, -1);
				// 2.判断Redis中查询的文章是否为空
				if (redisArticles == null || redisArticles.size() == 0) {
					// 3.如果为空,从mysql中查询最新文章 ，放入redis中
					System.err.println("从mysql中查询最新的文章*******");
					List<Articles> lastArticles = articlesService.lastList();

					// 放入redis中
					redisTemplate.opsForList().leftPushAll("new_articles", lastArticles.toArray());
					//Redis缓存功能，有效期5分钟
					redisTemplate.expire("new_articles", 5, TimeUnit.MINUTES);

					// 返回给前台
					request.setAttribute("lastArticles", lastArticles);
				} else {
					// 4.如果不为空直接返回到前台
					System.err.println("从redis中查询最新的文章-------");
					request.setAttribute("lastArticles", redisArticles);
				}

			};
		};

		// 轮播图
		Thread t4 = new Thread() {
			@Override
			public void run() {

				List<Slide> slides = articlesService.getSlides();
				request.setAttribute("slides", slides);
			};
		};

		t1.start();
		t2.start();
		t3.start();
		t4.start();

		t1.join();
		t2.join();
		t3.join();
		t4.join();

		return "index";
	}

	/**
	 * 获取栏目下分类 文章
	 * 
	 * @param request
	 * @param channelId
	 * @param catId
	 * @param pageNum
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping("/channel")
	public String channel(HttpServletRequest request, int channelId, @RequestParam(defaultValue = "0") int catId,
			@RequestParam(defaultValue = "1") int pageNum) throws InterruptedException {

		Thread t1 = new Thread() {
			public void run() {
				// 获取所有的栏目
				List<Channel> channels = articlesService.getChannels();
				request.setAttribute("channels", channels);
			};
		};

		Thread t2 = new Thread() {
			public void run() {
				// 当前栏目下 当前分类下的文章
				PageInfo<Articles> articlePage = articlesService.getArticles(channelId, catId, pageNum);
				request.setAttribute("articlePage", articlePage);
			};
		};

		Thread t3 = new Thread() {
			public void run() {
				// 获取最新文章
				List<Articles> lastArticles = articlesService.lastList();
				request.setAttribute("lastArticles", lastArticles);
			};
		};

		Thread t4 = new Thread() {
			public void run() {
				// 轮播图
				List<Slide> slides = articlesService.getSlides();
				request.setAttribute("slides", slides);

			};
		};

		// 获取当前栏目下的所有的分类 catId
		Thread t5 = new Thread() {
			public void run() {
				//
				List<Category> categoris = articlesService.getCategoriesByChannelId(channelId);
				request.setAttribute("categoris", categoris);
				System.err.println("categoris is " + categoris);
			};
		};

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();

		t1.join();
		t2.join();
		t3.join();
		t4.join();
		t5.join();

		// 参数回传
		request.setAttribute("catId", catId);
		request.setAttribute("channelId", channelId);

		return "channel";
	}
}
