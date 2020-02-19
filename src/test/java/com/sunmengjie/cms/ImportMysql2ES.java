package com.sunmengjie.cms;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sunmengjie.cms.entity.Articles;
import com.sunmengjie.cms.mapper.ArticleRep;
import com.sunmengjie.cms.service.ArticlesService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-beans.xml")
public class ImportMysql2ES {

	@Autowired
	ArticlesService articlesService;
	
	@Autowired
	ArticleRep articleRep;

	
	
	@Test
	public void importMysql2Es() {
//		1.从mysql中查询所有已经审核的文章
		List<Articles> findAllArticlesWithStatus = articlesService.findAllArticlesWithStatus(1);
//		2.查询出来的文章保存到es索引库中
		articleRep.saveAll(findAllArticlesWithStatus);
	}
	
	@Test
	public void selectContent() {
		String count ="测试";
		List<Articles> findByContent = articlesService.findByContent(count);
		for (Articles articles : findByContent) {
			System.out.println(articles);
		}
	}
	
	
	/**分页查询*/ //
	@Test
	public void findAllpage() {
		Pageable pageable = new PageRequest(5,5);
		
		Page<Articles> page = articleRep.findAll(pageable);
		for (Articles articles : page) {
			System.out.println(articles);
		}
	}
}
