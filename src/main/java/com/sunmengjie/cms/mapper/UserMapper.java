package com.sunmengjie.cms.mapper;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.sunmengjie.cms.entity.User;

public interface UserMapper {

	@Select("SELECT id,username,`password` FROM cms_user WHERE username=#{username} limit 1")
	User findUserByName(String username);

	@Insert("INSERT INTO cms_user (username,`password`,locked,create_time,score,role) VALUES(#{username},#{password},0,now(),0,0)")
	int register(@Valid User user);

	@Select("SELECT  id,username,`password`,nickname,birthday,"
			+ "gender,locked,create_time createTime,update_time updateTime,"
			+ "url,role FROM cms_user WHERE username=#{username} and  password=#{password} limit 1	")
	User findByPwd(User user);

}
