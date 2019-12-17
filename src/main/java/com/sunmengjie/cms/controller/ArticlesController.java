package com.sunmengjie.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunmengjie.cms.common.CmsError;
import com.sunmengjie.cms.common.CmsMessage;
import com.sunmengjie.cms.entity.Articles;
import com.sunmengjie.cms.service.ArticlesService;




@RequestMapping("/articles")
@Controller
public class ArticlesController {

	@Autowired
	private ArticlesService articlesService;
	
	@RequestMapping("/getDetail")
	@ResponseBody
	public CmsMessage getDetail(int id) {
		System.out.println(111111);
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
}
