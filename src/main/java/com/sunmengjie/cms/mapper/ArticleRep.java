package com.sunmengjie.cms.mapper;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.sunmengjie.cms.entity.Articles;

public interface ArticleRep extends ElasticsearchRepository<Articles, Integer>{

	List<Articles> findByContent(String count);

	List<Articles> findByTitle(String key);

}
