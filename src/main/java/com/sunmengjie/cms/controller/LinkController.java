package com.sunmengjie.cms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.sunmengjie.cms.entity.Link;
import com.sunmengjie.cms.service.LinkService;

@Controller
@RequestMapping("link")
public class LinkController {

	@Autowired
	private LinkService linkService;
	
	
	@RequestMapping("getLink")
	public String getLink(HttpServletRequest request,@RequestParam(defaultValue = "1")int pageNum) {
		
		PageInfo<Link> linkPage=linkService.getLink(pageNum);
		System.out.println(linkPage);
		request.setAttribute("linkPage", linkPage);
		
		return "/admin/link";
	}
}
