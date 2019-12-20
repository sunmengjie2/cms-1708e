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
import com.sunmengjie.cms.entity.Comment;
import com.sunmengjie.cms.entity.Slide;
import com.sunmengjie.cms.mapper.ArticlesMapper;
import com.sunmengjie.cms.mapper.SlideMapper;
import com.sunmengjie.cms.service.ArticlesService;

@Service
public class ArticlesServiceImpl implements ArticlesService {

	@Autowired
	private ArticlesMapper articlesMapper;

	@Autowired
	private SlideMapper slideMapper;
	
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
	
	//文章管理
	@Override
	public PageInfo<Articles> list(int status, int pageNum) {
		PageHelper.startPage(pageNum, CmsContant.PAGE_SIZE);
		
		return new PageInfo<Articles>(articlesMapper.list(status));
	}
	
	
	@Override
	public int setCheckStatus(int id, int status) {
		// TODO Auto-generated method stub
		return articlesMapper.setCheckStatus(id,status);
	}

	//
	@Override
	public Articles getInfoById(int id) {
		// TODO Auto-generated method stub
		return articlesMapper.getInfoById(id);
	}
	
	//设置文章   热门  非热门
	@Override
	public int setHot(int id, int status) {
		// TODO Auto-generated method stub
		return articlesMapper.setHot(id,status);
	}

	//热门文章
	@Override
	public PageInfo<Articles> hotList(int pageNum) {
		// TODO Auto-generated method stub
		
		PageHelper.startPage(pageNum, CmsContant.PAGE_SIZE);
		return new PageInfo<Articles>(articlesMapper.hotList());
	}
	
	//最新文章
	@Override
	public List<Articles> lastList() {
		// TODO Auto-generated method stub
		return articlesMapper.lastList(CmsContant.PAGE_SIZE);
	}
	
	//轮播图
	@Override
	public List<Slide> getSlides() {
		// TODO Auto-generated method stub
		return slideMapper.getSlides();
	}
	
	//获取栏目下的文章
	@Override
	public PageInfo<Articles> getArticles(int channelId, int catId, int pageNum) {
		
		PageHelper.startPage(pageNum, CmsContant.PAGE_SIZE);
		
		return new PageInfo<Articles>(articlesMapper.getArticles(channelId,catId));
	}
	
	//获取栏目下的分类
	@Override
	public List<Category> getCategoriesByChannelId(int channelId) {
		// TODO Auto-generated method stub
		return articlesMapper.getCategoriesByChannelId(channelId);
	}
	
	//发表评论
	@Override
	public int addComment(Comment comment) {
		// TODO Auto-generated method stub
		int resule = articlesMapper.addComment(comment);
		
		if(resule>0) {
			articlesMapper.increaseCommentCnt(comment.getArticleId());
		}
		return resule;
	}
	
	//根据文章Id  获取评论
	@Override
	public PageInfo<Comment> getComments(int articleId, int pageNum) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, CmsContant.PAGE_SIZE);
		return new PageInfo<Comment>(articlesMapper.getComments(articleId));
	}
	
	
	//获取文章的上一篇 和下一篇
	@Override
	public Integer  getArticle(int id, int articleId) {
		
		
		
		if(id<articleId) {
			Integer aid = articlesMapper.getPreArticle(articleId);
			return aid;
		}else {
			Integer aid = articlesMapper.getNextArticle(articleId);
			return aid;
		}
		
		
	}
}
