package com.clone.twitter.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.clone.twitter.entity.Likes;
import com.clone.twitter.entity.Posts;
import com.clone.twitter.entity.Users;

public interface LikesRepository extends JpaRepository<Likes, Integer> {

	void deleteByPostsAndUsers(Posts posts, Users users);
}
