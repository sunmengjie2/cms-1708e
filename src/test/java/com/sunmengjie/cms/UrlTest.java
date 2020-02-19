package com.sunmengjie.cms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sunmengjie.cms.service.ArticlesService;
import com.sunmengjie.cms.utils.StringUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-beans.xml")
public class UrlTest {
	
	@Autowired
	ArticlesService articlesService;

	//测试url
	@Test
	public  void  urlTest() {
		String url ="http.ff.com";
		
		boolean flag = StringUtils.isHttpUrl(url);
		System.out.println(flag);
	}
	
	//测试收藏方法
	
	@Test
	public void collectTest() {
		
		String name="今天月考1111ff";
		String url="http.ffff.com";
		int userId=2;
		
		if(StringUtils.isHttpUrl(url)==true) {
			System.out.println("1111");
			articlesService.addConllect(name, url, userId);
		}else {
			System.err.println(url+"url不合法");
		}
		
		
	}
	
	//测试删除收藏
	@Test
	public void delTest() {
		int id =24;
		
		int i = articlesService.delCollect(id);
		System.out.println(i);
	}
}
