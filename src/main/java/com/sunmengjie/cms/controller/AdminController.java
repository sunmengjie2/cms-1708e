package com.sunmengjie.cms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.sunmengjie.cms.common.CmsContant;
import com.sunmengjie.cms.common.CmsError;
import com.sunmengjie.cms.common.CmsMessage;
import com.sunmengjie.cms.entity.Articles;
import com.sunmengjie.cms.service.ArticlesService;

@RequestMapping("/admin")
@Controller
public class AdminController {

	@Autowired
	private ArticlesService articlesService;
	
	@RequestMapping("/index")
	public String index() {
		return "/admin/index";
	}
	
	@RequestMapping("/articles")
	public String articles(HttpServletRequest request,@RequestParam(defaultValue = "0")int status,
			@RequestParam(defaultValue = "1")int pageNum) {
		
		PageInfo<Articles> articlesPage =  articlesService.list(status,pageNum);
		
		request.setAttribute("status", status);
		request.setAttribute("articlesPage", articlesPage);
				
		return "admin/articles/list";
	}
	
	
	
	
	@RequestMapping("setArticeStatus")
	@ResponseBody
	public CmsMessage setsetArticeStatus(int id,int status) {
		/**
		 * 	数据合法性校验
		 */
		if(status!=1 &&status!=2) {
			return new CmsMessage(CmsError.NOT_VALIDATED_ARGURMENT,"status参数值不合法",null);
		}
		
		if(id<0) {
			return new CmsMessage(CmsError.NOT_VALIDATED_ARGURMENT,"id的参数值不合法",null);
		}
		
		Articles articles = articlesService.getInfoById(id);
		
		
		/**
		 * 
		 */
		if(articles.getStatus()==status) {
			return new CmsMessage(CmsError.NEEDNT_UPDATE,"数据无需更改",null);
		}
		
		/**
		 * 修改数据
		 */
		int result = articlesService.setCheckStatus(id,status);
		if(result<1) {
			return new CmsMessage(CmsError.FAILED_UPDATE_DB,"设置失败，请稍后再试",null);
		}
		
		return new CmsMessage(CmsError.SUCCESS,"成功",null);
	}
	
	
	

	@RequestMapping("/setArticesHot")
	@ResponseBody
	public CmsMessage setArticesHot(int id,int status) {
		/**
		 *   数据合法性校验
		 */
		if(status!=0 && status!=1) {
			
		}
		
		if(id<0) {
			
		}
		
		
		Articles articles = articlesService.getInfoById(id);
		if(articles ==null) {
			
		}
		
		if(articles.getStatus()==status) {
			
		}
		
		int result = articlesService.setHot(id,status);
		
		if(result<1) {
			return new CmsMessage(CmsError.FAILED_UPDATE_DB,"设置失败，请稍后再试",null);
		}
		
		return new CmsMessage(CmsError.SUCCESS, "成功", null);
	}
	
	
	
	
	
	
	
	/**
	 * 退出
	 * @param session
	 * @return
	 */
	@RequestMapping("exit")
	public String exit(HttpSession session) {
		session.removeAttribute(CmsContant.USER_KEY);
		
		return "/user/login";
	}
}
