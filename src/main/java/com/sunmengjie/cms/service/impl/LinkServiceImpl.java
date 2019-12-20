package com.sunmengjie.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunmengjie.cms.common.CmsContant;
import com.sunmengjie.cms.entity.Link;
import com.sunmengjie.cms.mapper.LinkMapper;
import com.sunmengjie.cms.service.LinkService;

@Service
public class LinkServiceImpl implements LinkService {

	@Autowired
	private LinkMapper linkMapper;

	@Override
	public PageInfo<Link> getLink(int pageNum) {
		PageHelper.startPage(pageNum, CmsContant.PAGE_SIZE);
		
		return new PageInfo<Link>(linkMapper.getLink());
	}
	
}
