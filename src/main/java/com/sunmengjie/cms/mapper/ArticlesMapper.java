package com.sunmengjie.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sunmengjie.cms.entity.Articles;
import com.sunmengjie.cms.entity.Category;
import com.sunmengjie.cms.entity.Channel;

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

	

	
}
