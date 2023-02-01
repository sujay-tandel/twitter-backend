package com.clone.twitter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clone.twitter.entity.Hashtags;

public interface HashtagsRepository extends JpaRepository<Hashtags, Integer>{

	public Hashtags findByTag(String tag);

	public List<Hashtags> findByTagIn(List<String> tags);
}
