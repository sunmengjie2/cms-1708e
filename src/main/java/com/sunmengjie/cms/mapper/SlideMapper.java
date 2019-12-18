package com.sunmengjie.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.sunmengjie.cms.entity.Slide;

public interface SlideMapper {

	/**
	 *  首页  获取轮播图
	 * @return
	 */
	@Select("SELECT id,title,picture,url FROM cms_slide ORDER BY id")
	List<Slide> getSlides();

	

}
