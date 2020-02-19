package com.sunmengjie.cms.kafka;

import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListener;

import com.alibaba.fastjson.JSON;
import com.sunmengjie.cms.entity.Articles;
import com.sunmengjie.cms.mapper.ArticleRep;
import com.sunmengjie.cms.service.ArticlesService;
import com.sunmengjie.cms.service.RedisArticleService;

public class ArticleListener implements MessageListener<String, String> {

	@Autowired
	ArticlesService articlesService;

	@Autowired
	RedisTemplate redisTemplate;

	@Autowired
	ArticleRep articleRep;
	

	@Override
	public void onMessage(ConsumerRecord<String, String> data) {

		String value = data.value();
		System.err.println("over！！！！" + value);

		if (value.startsWith("add")) {
			
			int indexOf = value.indexOf("=");
			Articles parseObject = JSON.parseObject(value.substring(indexOf+1), Articles.class);

			// 把审核通过的文章存到redis数据库
			redisTemplate.opsForList().leftPush("articles", parseObject);
			// System.err.println("收到消息================");
			// 把审核通过的Id删除es仓库数据
			articleRep.save(parseObject);
		}else if(value.startsWith("del")){
			int indexOf = value.indexOf("=");
			//删除
			articleRep.deleteById(Integer.parseInt(value.substring(indexOf+1)));
		}else if(value.startsWith("imp")){ 
			//以imp开头就是导入文章
			int indexOf = value.indexOf("=");
			//获取文章
			Articles parseObject = JSON.parseObject(value.substring(indexOf+1), Articles.class);
			//添加到数据库
			articlesService.addArticle(parseObject);
		}else if(value.startsWith("hits")) {
			//以hits开头就是访问量+1
			
			int indexOf = value.indexOf("=");
			
			//修改数据库访问数量
			articlesService.updHits(value.substring(indexOf+1));
			
		}else if(value.startsWith("redis")) {
			int indexOf = value.indexOf("=");
			//获取接受到的消息值   redis  key
			String key = value.substring(indexOf+1);
			System.err.println("收到的键值"+key);
			//通过key获取到值
			Articles article = (Articles) redisTemplate.opsForValue().get(key);
			int add = articlesService.add(article);
			System.out.println(article.getTitle()+"已导入完毕");
			if(add>0) {
				redisTemplate.delete(key);
			}
			
		}else{
			Articles parseObject = JSON.parseObject(value, Articles.class);
			articlesService.add(parseObject);
		}
	}  

}
