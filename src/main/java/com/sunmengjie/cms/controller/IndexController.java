package com.sunmengjie.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.sunmengjie.cms.entity.Articles;
import com.sunmengjie.cms.entity.Category;
import com.sunmengjie.cms.entity.Channel;
import com.sunmengjie.cms.entity.Slide;
import com.sunmengjie.cms.service.ArticlesService;

@Controller
public class IndexController {

	@Autowired
	private ArticlesService articlesService;
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws InterruptedException 
	 */
	@RequestMapping(value = {"index","/"})
	public String index(HttpServletRequest request,@RequestParam(defaultValue = "1")int pageNum) throws InterruptedException {
		
		Thread t1 = new Thread() {
			@Override
			public void run() {
				// 获取所有的栏目
				List<Channel> channels = articlesService.getChannels();
				request.setAttribute("channels", channels);
			};
		};
		
		Thread t2 = new Thread() {
			@Override
			public void run() {
				//获取热门文章
				PageInfo<Articles> articlesPage = articlesService.hotList(pageNum);
				request.setAttribute("articlesPage", articlesPage);
			};
		};
		
		Thread t3 = new Thread() {
			@Override
			public void run() {
				//获取最新文章
				List<Articles> lastArticles = articlesService.lastList();
				request.setAttribute("lastArticles", lastArticles);
			};
		};
		
		Thread t4 = new Thread() {
			@Override
			public void run() {
				//轮播图
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
	 * 获取栏目下分类       文章
	 * @param request
	 * @param channelId
	 * @param catId
	 * @param pageNum
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping("/channel")
	public String channel(HttpServletRequest request,int channelId,
			@RequestParam(defaultValue = "0")int catId,
			@RequestParam(defaultValue = "1")int pageNum) throws InterruptedException {
				
		
		
		Thread  t1 =  new Thread() {
			public void run() {
		// 获取所有的栏目
		List<Channel> channels = articlesService.getChannels();
		request.setAttribute("channels", channels);
			};
		};
		
		Thread  t2 =  new Thread() {
			public void run() {
		// 当前栏目下  当前分类下的文章
		PageInfo<Articles> articlePage= articlesService.getArticles(channelId,catId, pageNum);
		request.setAttribute("articlePage", articlePage);
			};
		};
		
		Thread  t3 =  new Thread() {
			public void run() {
		// 获取最新文章
		List<Articles> lastArticles = articlesService.lastList();
		request.setAttribute("lastArticles", lastArticles);
			};
		};
		
		Thread  t4 =  new Thread() {
			public void run() {
		// 轮播图
		List<Slide> slides = articlesService.getSlides();
		request.setAttribute("slides", slides);
		
			};
		};
		
		// 获取当前栏目下的所有的分类 catId
		Thread  t5 =  new Thread() {
			public void run() {
		// 
		List<Category> categoris= articlesService.getCategoriesByChannelId(channelId);
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
