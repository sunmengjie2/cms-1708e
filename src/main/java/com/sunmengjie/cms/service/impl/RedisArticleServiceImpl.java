package com.sunmengjie.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.sunmengjie.cms.entity.Articles;
import com.sunmengjie.cms.service.RedisArticleService;
@Service
public class RedisArticleServiceImpl implements RedisArticleService {

	@Autowired
	RedisTemplate redisTemplate;
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	
	@Override
	public void save(Articles article) {
		String key = "redis_a";
		redisTemplate.opsForValue().set(key, article);
		
		kafkaTemplate.send("article","redis="+key );
		
	}

}
