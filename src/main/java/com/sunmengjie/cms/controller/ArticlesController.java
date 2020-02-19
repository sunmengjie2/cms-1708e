package com.sunmengjie.cms.controller;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.sunmengjie.cms.common.CmsContant;
import com.sunmengjie.cms.common.CmsError;
import com.sunmengjie.cms.common.CmsMessage;
import com.sunmengjie.cms.entity.Articles;
import com.sunmengjie.cms.entity.Comment;
import com.sunmengjie.cms.entity.Complain;
import com.sunmengjie.cms.entity.User;
import com.sunmengjie.cms.service.ArticlesService;
import com.sunmengjie.cms.utils.StringUtils;


@RequestMapping("/articles")
@Controller
public class ArticlesController extends BaseController{

	@Autowired
	private ArticlesService articlesService;
	
	@Autowired
    RedisTemplate redisTemplate;
	
	
	//注入spring的线程池
	@Autowired
	ThreadPoolTaskExecutor executor;
	
	@RequestMapping("/getDetail")
	@ResponseBody
	public CmsMessage getDetail(int id) {
		
		if(id<=0) {
			
		}
		
		//获取文章详情
		Articles articles = articlesService.getById(id);
		
		//不存在
		if(articles==null) {
			return new CmsMessage(CmsError.NOT_EXIST,"文章不存在",null);
		}
		
		//返回数据
		return new CmsMessage(CmsError.SUCCESS,"",articles);
	}
	
	/**
	 * 文章详情查询
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("detail")
	public String detail(HttpServletRequest request,int id) {
		
		Articles articles = articlesService.getById(id);
		
		request.setAttribute("article", articles);
		
		
		/**
		 * 现在请你利用Redis提高性能，当用户浏览文章时，
		 * 将“Hits_${文章ID}_${用户IP地址}”为key，查询Redis里有没有该key，如果有key，则不做任何操作。
		 * 如果没有，则使用Spring线程池异步执行数据库加1操作，
		 * 并往Redis保存key为Hits_${文章ID}_${用户IP地址}，value为空值的记录，而且有效时长为5分钟。
		 */
		//获取用户ip地址  的方法
		String user_ip = request.getRemoteAddr();
		//准备redis'的key
		String key = "Hits"+id+user_ip;
		//查询redis中的该key
		String redisKey = (String) redisTemplate.opsForValue().get(key);
		if(redisKey==null) {
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					//在这里就可以写具体的逻辑了
					//数据库+1操作(根据id从mysql中查询文章对象)
					//设置浏览量+1
					articles.setHits(articles.getHits()+1);
					//更新到数据库
					articlesService.updaHits(articles);
					//并往Redis保存key为Hits_${文章ID}_${用户IP地址}，value为空值的记录，而且有效时长为5分钟。
					redisTemplate.opsForValue().set(key, "",5, TimeUnit.MINUTES);
				}
			});
		}
		
		return "detail";
	}
	
	
	
	@RequestMapping("comments")
	public String comments(HttpServletRequest request,int id,int page) {
		PageInfo<Comment> commentPage =  articlesService.getComments(id,page);
		request.setAttribute("commentPage", commentPage);
		return "comments";
	}	
	
	@RequestMapping("postcomment")
	@ResponseBody
	public CmsMessage postcomment(HttpServletRequest request,int articleId,String content) {
		
		User loginUser  = (User)request.getSession().getAttribute(CmsContant.USER_KEY);
		
		if(loginUser==null) {
			return new CmsMessage(CmsError.NOT_LOGIN, "您尚未登录！", null);
		}
		
		Comment comment = new Comment();
		comment.setUserId(loginUser.getId());
		comment.setContent(content);
		comment.setArticleId(articleId);
		int result = articlesService.addComment(comment);
		if(result > 0)
			return new CmsMessage(CmsError.SUCCESS, "成功", null);
		
		return new CmsMessage(CmsError.FAILED_UPDATE_DB, "异常原因失败，请与管理员联系", null);
		
	}
	
	@RequestMapping("pagearticle")
	@ResponseBody
	public CmsMessage pagearticle(int id, int articleId) {
		
		Integer aid = articlesService.getArticle(id,articleId);
			
		if(aid == null) {
			if(id<articleId) {
				return new CmsMessage(CmsError.NOT_EXIST, "该文章已经是第一篇了", null);
			}else {
				return new CmsMessage(CmsError.NOT_EXIST, "该文章已经是最后一篇了", null);
			}
		}
			
			
			return new CmsMessage(CmsError.SUCCESS,"",aid);
	}
	
	
	
	/**
	 * 跳转到投诉页面
	 * @param request
	 * @param articleId
	 * @return
	 */
	@RequestMapping(value = "complain",method =RequestMethod.GET)
	public String complain(HttpServletRequest request,int articleId) {
		Articles article = articlesService.getById(articleId);
		
		request.setAttribute("article", article);
		request.setAttribute("complain", new Complain());
		
		return "article/complain";
	}
	
	
	
	@RequestMapping(value = "complain",method =RequestMethod.POST)
	public String complain(HttpServletRequest request,
			 @Valid	@ModelAttribute("complain") Complain complain,BindingResult result) {
		
		
		if(!StringUtils.isUrl(complain.getUrlip())) {
			result.rejectValue("urlip", "", "urlip地址不合法");
		}
		
		if(result.hasErrors()) {
			return "article/complain";
		}
		
		User loginUser = (User) request.getSession().getAttribute(CmsContant.USER_KEY);
		//System.out.println("-****************"+loginUser);
		
		//加上投诉人
		if(loginUser!=null) {
			complain.setUserId(loginUser.getId());
		}else {
			complain.setUserId(0);
		}
		
		
		//System.out.println("==============="+complain);
		
		articlesService.addComplain(complain);
		
		return "redirect:/articles/detail?id="+complain.getArticleId();
	}


	
	
	@RequestMapping("complains")
	public String 	complains(HttpServletRequest request,int articleId,
			@RequestParam(defaultValue="1") int page) {
		PageInfo<Complain> complianPage =   articlesService.getComplains(articleId, page);
		request.setAttribute("complianPage", complianPage);
		return "article/complainslist";
	}
	
	
	
}
