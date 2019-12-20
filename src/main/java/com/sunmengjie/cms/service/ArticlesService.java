package com.sunmengjie.cms.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.sunmengjie.cms.entity.Articles;
import com.sunmengjie.cms.entity.Category;
import com.sunmengjie.cms.entity.Channel;
import com.sunmengjie.cms.entity.Comment;
import com.sunmengjie.cms.entity.Slide;

public interface ArticlesService {

	/**
	 * 根据用户获取文章列表
	 * @param id
	 * @param page
	 * @return
	 */
	PageInfo<Articles> listByUser(Integer id, int page);

	
	//我的文章删除
	int delete(int id);



	
	
	/**
	 * 获取所有的栏目
	 * @return
	 */
	List<Channel> getChannels();
	
	/**
	 * 获取所有分类
	 * @param cid
	 * @return
	 */
	List<Category> getCategorisByCid(int cid);

	/**
	 * 发表文章
	 * @param articles
	 * @return
	 */
	int add(Articles articles);

	/**
	 * 	 通过修改 文章的 id 查询文章内容
	 * @param id
	 * @return
	 */
	Articles getById(int id);


	/**
	 * 修改后提交
	 * @param article
	 * @param id
	 * @return
	 */
	int update(Articles articles, Integer id);


	/**
	 * 文章管理
	 * @param status
	 * @param pageNum
	 * @return
	 */
	PageInfo<Articles> list(int status, int pageNum);

	
	
	
	
	int setCheckStatus(int id, int status);

	/**
	 * 获取文章的简要信息  常常用于判断文章的存在性
	 * @param id
	 * @return
	 */
	Articles getInfoById(int id);


	int setHot(int id, int status);


	/**
	 * 热门文章
	 * @param pageNum
	 * @return
	 */
	PageInfo<Articles> hotList(int pageNum);


	/**
	 * 最新文章
	 * @return
	 */
	List<Articles> lastList();


	/**
	 * 轮播图
	 * @return
	 */
	List<Slide> getSlides();


	/**
	 * 获取栏目下的文章
	 * @param channelId
	 * @param catId
	 * @param pageNum
	 * @return
	 */
	PageInfo<Articles> getArticles(int channelId, int catId, int pageNum);

	/**
	 * 获取栏目下的 分类
	 * @param channelId
	 * @return
	 */
	List<Category> getCategoriesByChannelId(int channelId);


	/**
	 * 根据文章id 获取评论
	 * @param articleId  文章id  
	 * @param page
	 * @return
	 */
	PageInfo<Comment> getComments(int articleId, int pagenNum);

	/**
	 * 发表评论
	 * @param comment
	 * @return
	 */
	int addComment(Comment comment);


	Integer getArticle(int id, int articleId);









}
