package com.sunmengjie.cms.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunmengjie.cms.common.CmsContant;
import com.sunmengjie.cms.entity.Articles;
import com.sunmengjie.cms.entity.Category;
import com.sunmengjie.cms.entity.Channel;
import com.sunmengjie.cms.entity.Collect;
import com.sunmengjie.cms.entity.Comment;
import com.sunmengjie.cms.entity.Complain;
import com.sunmengjie.cms.entity.Slide;
import com.sunmengjie.cms.entity.User;
import com.sunmengjie.cms.mapper.ArticleRep;
import com.sunmengjie.cms.mapper.ArticlesMapper;
import com.sunmengjie.cms.mapper.CollectMapper;
import com.sunmengjie.cms.mapper.SlideMapper;
import com.sunmengjie.cms.service.ArticlesService;
import com.sunmengjie.cms.service.RedisArticleService;

@Service
public class ArticlesServiceImpl implements ArticlesService{

	@Autowired
	private ArticlesMapper articlesMapper;
	
	@Autowired
	private SlideMapper slideMapper;
	
	@Autowired
	RedisTemplate redisTemplate;
	
	@Autowired
	ArticleRep articleRep;
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	CollectMapper collectMapper;
	
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
		return articlesMapper.getAllChannels();
	}

	//获取分类
	@Override
	public List<Category> getCategorisByCid(int cid) {
		return articlesMapper.getCategorisByCid(cid);
	}

	//发表文章
	@Override
	public int add(Articles articles) {
		return articlesMapper.add(articles);
	}

	//通过id 获取 文章对象 
	@Override
	public Articles getById(int id) {
		//kafka生产者  把id 发送
		//kafkaTemplate.send("article", "hits="+String.valueOf(id));
		
		
		return articlesMapper.findById(id);
	}
	
	//修改后提交
	@Override
	public int update(Articles articles, Integer userId) {
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
	
	//审核状态
	@Override
	public int setCheckStatus(int id, int status) {
		if(status==1) {
			Articles article = articlesMapper.findById(id);
			String jsonString = JSON.toJSONString(article);
			jsonString="add="+jsonString;
			kafkaTemplate.send("article",jsonString );
			System.err.println("发送消息*****************");
		}
		return articlesMapper.setCheckStatus(id,status);
	}

	//
	@Override
	public Articles getInfoById(int id) {
		return articlesMapper.getInfoById(id);
	}
	
	//设置文章   热门  非热门
	@Override
	public int setHot(int id, int status) {
		return articlesMapper.setHot(id,status);
	}

	//热门文章
	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<Articles> hotList(int pageNum) {
		
		PageHelper.startPage(pageNum, CmsContant.PAGE_SIZE);
		//redis 作为缓存来优化热门文章
		
		//1.先从redis中查找热门文章
		
		List<Articles> redisArticles = redisTemplate.opsForList().range("hotArticle", 0,-1);
		
		//2.判断Redis中查询的结果是否为空
		if(redisArticles==null || redisArticles.size()==0) {
			
			//3.如果为空，从mysql中查询热门文章  ， 在存入Redis中
			System.err.println("从mysql中查询热门的文章*******");
			
			List<Articles> hotList = articlesMapper.hotList();
//			for (Articles articles : hotList) {
//				System.out.println(articles);
//			}
			//放入Redis中
			redisTemplate.opsForList().leftPushAll("hotArticle", hotList);
			//设置过期时间
			redisTemplate.expire("hotArticle", 5, TimeUnit.MINUTES);
			
			return new PageInfo<Articles>(hotList) ;
		}else {
			//4.如果不为空则直接返回到前台
			System.err.println("从redis中查询热门的文章-------");
			return new PageInfo<Articles>(redisArticles);
		}
	}
	
	//最新文章
	@Override
	public List<Articles> lastList() {
		return articlesMapper.lastList(CmsContant.PAGE_SIZE);
	}
	
	//轮播图
	@Override
	public List<Slide> getSlides() {
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
		return articlesMapper.getCategoriesByChannelId(channelId);
	}
	
	//发表评论
	@Override
	public int addComment(Comment comment) {
		int resule = articlesMapper.addComment(comment);
		
		if(resule>0) {
			articlesMapper.increaseCommentCnt(comment.getArticleId());
		}
		return resule;
	}
	
	//根据文章Id  获取评论
	@Override
	public PageInfo<Comment> getComments(int articleId, int pageNum) {
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

	@Override
	public int addComplain(Complain complain) {
		
		//添加投诉到数据库
		int result = articlesMapper.addCoplain(complain);
		
		if(result>0) {
			articlesMapper.increaseComplainCnt(complain.getArticleId());
		}
		return 0;
	}
	
	@Override
	public PageInfo<Complain> getComplains(int articleId, int page) {
		PageHelper.startPage(page, CmsContant.PAGE_SIZE);
		return new PageInfo<Complain>(articlesMapper.getComplains(articleId));
	}
	
	@Override
	public PageInfo<Complain> getComplain(int pageNum,String com, String t1, String t2, String dis) {
		
		PageHelper.startPage(pageNum, CmsContant.PAGE_SIZE);
		
		return new PageInfo<Complain>(articlesMapper.getComplain(com,t1,t2, dis));
	}
	
	
	@Override
	public Complain getCom(int userId) {
		return articlesMapper.getCom(userId);
	}
	
	@Override
	public User getUser(int userId) {
		return articlesMapper.getUser(userId);
	}

	//获取所有文章 保存到es索引库中
	@Override
	public List<Articles> findAllArticlesWithStatus(int i) {
		// TODO Auto-generated method stub
		return articlesMapper.findAllArticlesWithStatus(i);
	}

	//测试查询es索引库
	@Override
	public List<Articles> findByContent(String count) {
		
		return articleRep.findByContent(count);
	}
	
	//接收到的文章添加到数据库
	@Override
	public int addArticle(Articles parseObject) {
		
		
		return articlesMapper.addArticle(parseObject);
	}
	
	//访问量  增加 1
	@Override
	public int updHits(String substring) {
		
		return articlesMapper.updHits(substring);
	}
	
	//访问量  增加 1
	@Override
	public int updaHits(Articles articles) {
		
		return articlesMapper.updaHits(articles);
	}
	
	
	//收藏文章
	@Override
	public List<Collect> getCollect(Integer userId, int pageNum) {
		
		//根据用户ID 查询我的收藏夹文章  分页
		
		
		return collectMapper.getCollect(userId);
	}
	
	//收藏文章 删除
	@Override
	public int delCollect(int id) {
		
		return collectMapper.delCollect(id);
	}
	
	//收藏文章
	@Override
	public int addConllect(String name, String url, Integer userId) {
		
		return collectMapper.addCollect(name,url,userId);
	}
}
