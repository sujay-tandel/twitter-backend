package com.clone.twitter.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.clone.twitter.entity.HashtagPosts;
import com.clone.twitter.entity.Hashtags;
import com.clone.twitter.entity.Posts;

public interface HashtagPostsRepository extends JpaRepository<HashtagPosts, Integer> {

	public List<Posts> findByHashtags(Hashtags hashtag);

	public List<Hashtags> findByPosts(Posts post);
}
