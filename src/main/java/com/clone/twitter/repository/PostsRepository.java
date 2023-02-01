package com.clone.twitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clone.twitter.entity.Posts;

public interface PostsRepository extends JpaRepository<Posts, Integer>{

}
