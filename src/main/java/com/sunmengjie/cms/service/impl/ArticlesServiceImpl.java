package com.sunmengjie.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunmengjie.cms.common.CmsContant;
import com.sunmengjie.cms.entity.Articles;
import com.sunmengjie.cms.entity.Category;
import com.sunmengjie.cms.entity.Channel;
import com.sunmengjie.cms.mapper.ArticlesMapper;
import com.sunmengjie.cms.service.ArticlesService;

@Service
public class ArticlesServiceImpl implements ArticlesService {

	@Autowired
	private ArticlesMapper articlesMapper;

	/**
	 * 我的文章
	 */
	@Override
	public PageInfo<Articles> listByUser(Integer id, int pageNum) {
		PageHelper.startPage(pageNum, CmsContant.PAGE_SIZE);
		
		
		//根据用户ID 查询我的文章  分页
		PageInfo<Articles> pageInfo = new PageInfo<Articles>(articlesMapper.listByUser(id));
		
		
		return pageInfo;
	}

	//我的文章   删除
	@Override
	public int delete(int id) {
		
		return articlesMapper.updateStatus(id,CmsContant.ARTICLE_STATUS_DEL);
	}

	//获取栏目
	@Override
	public List<Channel> getChannels() {
		// TODO Auto-generated method stub
		return articlesMapper.getAllChannels();
	}

	//获取分类
	@Override
	public List<Category> getCategorisByCid(int cid) {
		// TODO Auto-generated method stub
		return articlesMapper.getCategorisByCid(cid);
	}

	//发表文章
	@Override
	public int add(Articles articles) {
		// TODO Auto-generated method stub
		return articlesMapper.add(articles);
	}

	//通过id 获取 文章对象
	@Override
	public Articles getById(int id) {
		// TODO Auto-generated method stub
		return articlesMapper.findById(id);
	}
	
	//修改后提交
	@Override
	public int update(Articles articles, Integer userId) {
		// TODO Auto-generated method stub
		Articles articleSrc = this.getById(articles.getId());
		if(articleSrc.getUserId() != userId) {
			// todo 如果  不是自己的文章 需要。。。。。
		}
		return articlesMapper.update(articles);
	}
	
}
