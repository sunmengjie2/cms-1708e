package com.sunmengjie.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.sunmengjie.cms.entity.Link;

public interface LinkMapper {

	@Select("SELECT id,url,name,created FROM cms_link")
	List<Link> getLink();

}
