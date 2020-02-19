package com.sunmengjie.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sunmengjie.cms.entity.Collect;

public interface CollectMapper {


	@Select("SELECT  id,userId,url,NAME,created FROM cms_collect WHERE userId =#{userId} ORDER BY created DESC")
	List<Collect> getCollect(Integer userId);

	@Delete("DELETE FROM  cms_collect   WHERE id =#{id}")
	int delCollect(int id);

	@Insert("INSERT INTO cms_collect(userId,url,NAME,created)  VALUE(#{userId} ,#{url},#{name},NOW())")
	int addCollect(@Param("name")String name, @Param("url")String url, @Param("userId")Integer userId);

	
}
