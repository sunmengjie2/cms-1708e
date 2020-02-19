package com.sunmengjie.cms;

import java.io.File;
import java.util.Date;

import org.apache.log4j.lf5.util.DateFormatManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.sunmengjie.cms.entity.Articles;
import com.sunmengjie.cms.utils.FileUtillo;
import com.sunmengjie.cms.utils.RandomUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-beans.xml")
public class ImportArtices {

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	
	//读取文章 存到数据库
	@Test
	public void importArtices() throws Exception {
		File file = new File("D:\\dev\\kk");
		File[] files = file.listFiles();
		for (File file2 : files) {
			String title = file2.getName().replace(".txt", "");
			//System.out.println(title);
			//读取文章内容
			String content = FileUtillo.readFile(file2, "UTF8");
			//System.out.println(content);
			//声明一个文章对象
			Articles article = new Articles();
			 article.setTitle(title);
			 article.setContent(content);
			article.setPicture("20191101/88dbc45d-5784-424b-89dc-419f782d73bb.jpeg");
			article.setChannelId(RandomUtils.getNum(1, 8));
			article.setCategoryId(RandomUtils.getNum(1,28));
			article.setUserId(RandomUtils.getNum(1,6));
			article.setArticleType(0000);
			article.setStatus(RandomUtils.getNum(0,1));
			article.setHits(RandomUtils.getNum(0,30));
			article.setHot(RandomUtils.getNum(0, 1));
			
			 //把对象转成JSON格式
			 String jsonString = JSON.toJSONString(article);
			kafkaTemplate.send("article", "imp="+jsonString);
			
		}
		
	}
	
	
}
