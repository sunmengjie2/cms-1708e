package com.sunmengjie.cms.mapper;

import java.util.List;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sunmengjie.cms.entity.Articles;
import com.sunmengjie.cms.entity.Category;
import com.sunmengjie.cms.entity.Channel;
import com.sunmengjie.cms.entity.Comment;
import com.sunmengjie.cms.entity.Complain;

public interface ArticlesMapper {
	
	/**
	 * 根据用户获取文章的列表
	 * @param id
	 * @return
	 */
	List<Articles> listByUser(Integer id);

	/**
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@Update("UPDATE cms_article SET deleted=#{status} WHERE id=#{id}")
	int updateStatus(@Param("id")int id, @Param("status")int status);


	/**
	 * 获取所有栏目的方法
	 * @return
	 */
	@Select("SELECT id,name FROM cms_channel")
	List<Channel> getAllChannels();

	/**
	 * 根据栏目id 获取分类
	 * @param cid 栏目id
	 * @return
	 */
	@Select("SELECT id,name FROM cms_category WHERE channel_id = #{cid}")
	List<Category> getCategorisByCid(int cid);

	/**
	 * 
	 * @param articles
	 * @return
	 */
	@Insert("INSERT INTO cms_article(title,content,picture,channel_id,category_id,user_id,hits,hot,status,deleted,created,updated,commentCnt,articleType) "
			+ "VALUES(#{title},#{content},#{picture},#{channelId},#{categoryId},#{userId},0,0,0,0,now(),now(),0,#{articleType})")
	int add(Articles articles);

	
	Articles findById(int id);

	@Update("UPDATE cms_article SET title=#{title},content=#{content},picture=#{picture},channel_id=#{channelId},"
			+ "category_id=#{categoryId},status=0,updated=now() WHERE id=#{id}")
	int update(Articles articles);

	List<Articles> list(int status);

	/**
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@Update("UPDATE cms_article SET status=#{status} WHERE id=#{id}")
	int setCheckStatus(@Param("id")int id, @Param("status")int status);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT id,title,channel_id channelId , category_id categoryId,status ,hot "
			+ " FROM cms_article WHERE id = #{id}")
	Articles getInfoById(int id);

	/**
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@Update("UPDATE cms_article SET hot=#{hot}  WHERE id=#{id} ")
	int setHot(@Param("id")int id, @Param("hot")int status);

	/**
	 * 首页  热门文章
	 * @return
	 */
	List<Articles> hotList();

	/**
	 * 首页   最新文章
	 * @param pageSize
	 * @return
	 */
	List<Articles> lastList(int pageSize);

	/**
	 * 根据分类和栏目获取文章
	 * @param channelId
	 * @param catId
	 * @return
	 */
	List<Articles> getArticles(@Param("channelId")int channelId, @Param("catId")int catId);

	/**
	 * 获取栏目下的分类
	 * @param channelId
	 * @return
	 */
	@Select("SELECT id,name FROM cms_category where channel_id=#{value}")
	@ResultType(Category.class)
	List<Category> getCategoriesByChannelId(int channelId);

	
	/**
	 * 添加评论
	 * @param comment
	 * @return
	 */
	@Insert("INSERT INTO cms_comment(articleId,userId,content,created) "
			+ "VALUES(#{articleId},#{userId},#{content},NOW())")
	int addComment(Comment comment);

	/**
	 * 增加文章的评论数量
	 * @param articleId
	 */
	@Update("UPDATE cms_article SET commentCnt=commentCnt+1 WHERE id=#{articleId}")
	int increaseCommentCnt(int articleId);

	/**
	 * 根据文章id  获取评论
	 * @param articleId
	 * @return
	 */
	@Select("SELECT c.id,c.articleId,c.userId,u.username as userName,c.content,c.created FROM cms_comment as c "
			+ " LEFT JOIN cms_user as u ON u.id=c.userId "
			+ " WHERE articleId=#{value} ORDER BY c.created DESC ")
	List<Comment> getComments(int articleId);

	@Select("SELECT id FROM cms_article "
			+ "  WHERE status=1 AND deleted=0 AND id < #{articleId} ORDER BY id DESC LIMIT 1")
	Integer getPreArticle(int articleId);

	@Select("SELECT id FROM cms_article "
			+ " WHERE status=1 AND deleted=0 AND id > #{value} limit 1 ")
	Integer getNextArticle(int articleId);

	/**
	 * 添加投诉
	 * @param complain
	 * @return
	 */
	@Insert("INSERT INTO cms_complain(article_id,user_id,complain_type,"
			+ "compain_option,src_url,picture,content,email,mobile,created)"
			+ "   VALUES(#{articleId},#{userId},"
			+ "#{complainType},#{compainOption},#{srcUrl},#{picture},#{content},#{email},#{mobile},now())")
	int addCoplain(@Valid Complain complain);

	/**
	 * 	文章投诉数量+1
	 * @param articleId
	 */
	@Update("UPDATE cms_article SET complainCnt=complainCnt+1,status=if(complainCnt>10,2,status)  "
			+ " WHERE id=#{value}")
	void increaseComplainCnt(Integer articleId);

	
	List<Complain> getComplains(int articleId);

	

	
}
