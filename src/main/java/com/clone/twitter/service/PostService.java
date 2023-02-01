package com.clone.twitter.service;

import java.util.List;

import com.clone.twitter.model.PostModel;

public interface PostService {

	public List<PostModel> getAllPosts();

	public PostModel getPost(Integer postId);

	public PostModel addPost(PostModel postModel);

	public boolean deletePost(Integer postId, Integer userId);

	public long addLike(Integer postId, Integer userId);

	public long removeLike(Integer postId, Integer userId);
}
