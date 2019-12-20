package com.sunmengjie.cms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.sunmengjie.cms.common.CmsContant;
import com.sunmengjie.cms.common.CmsError;
import com.sunmengjie.cms.common.CmsMessage;
import com.sunmengjie.cms.entity.Articles;
import com.sunmengjie.cms.entity.Comment;
import com.sunmengjie.cms.entity.User;
import com.sunmengjie.cms.service.ArticlesService;




@RequestMapping("/articles")
@Controller
public class ArticlesController {

	@Autowired
	private ArticlesService articlesService;
	
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
	
	
}
