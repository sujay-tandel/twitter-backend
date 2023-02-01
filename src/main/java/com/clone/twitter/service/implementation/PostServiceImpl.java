package com.clone.twitter.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clone.twitter.entity.HashtagPosts;
import com.clone.twitter.entity.Likes;
import com.clone.twitter.entity.Posts;
import com.clone.twitter.entity.Users;
import com.clone.twitter.exception.ErrorSavingEntityToDatabaseException;
import com.clone.twitter.exception.ResourceNotFoundException;
import com.clone.twitter.model.PostModel;
import com.clone.twitter.model.UserModel;
import com.clone.twitter.repository.HashtagPostsRepository;
import com.clone.twitter.repository.LikesRepository;
import com.clone.twitter.repository.PostsRepository;
import com.clone.twitter.repository.UsersRepository;
import com.clone.twitter.service.HashtagService;
import com.clone.twitter.service.PostService;
import com.clone.twitter.service.UserService;
import com.clone.twitter.utility.Mapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

	@Autowired private PostsRepository postsRepository;

	@Autowired private HashtagPostsRepository hashtagPostRepository;

	@Autowired private UserService userService;

	@Autowired private HashtagService hashtagService;

	@Autowired private LikesRepository likeRepository;

	@Autowired private UsersRepository usersRepository;

	@Autowired
	@Qualifier("PostMapper")
	private Mapper<Posts, PostModel> postMapper;

	@Autowired
	@Qualifier("UserMapper")
	private Mapper<Users, UserModel> userMapper;

	@Override
	public List<PostModel> getAllPosts() {
		var posts = postsRepository.findAll();
		List<PostModel> postModels = new ArrayList<>();
		Optional.ofNullable(posts)
		.ifPresent(
				post -> post.forEach(eachPost -> postModels.add(postMapper.transform(eachPost))));
		return postModels;
	}

	@Override
	public PostModel getPost(Integer postId) {

		var post = postsRepository.findById(postId);
		if (post.isPresent()) return postMapper.transform(post.get());
		throw new ResourceNotFoundException("Post ID is Invalid");
	}

	@Override
	@Transactional
	public PostModel addPost(PostModel postModel) {

		List<HashtagPosts> hashtagPosts = new ArrayList<>();
		var post = postMapper.transformBack(postModel);
		post.setUsers(usersRepository.findById(postModel.getUserId()).get());
		Optional.ofNullable(hashtagService.getHashtagsByTags(postModel.getHashtags()))
		.ifPresent(
				hashtags -> {
					hashtags.forEach(
							hashtag -> {
								HashtagPosts hashtagPost = new HashtagPosts();
								hashtagPost.setHashtags(hashtag);
								hashtagPost.setPosts(post);
								hashtagPosts.add(hashtagPost);
							});
				});
		post.setPostHashtags(hashtagPosts);
		hashtagPostRepository.saveAll(hashtagPosts);
		return postMapper.transform(postsRepository.save(post));
	}

	@Override
	@Transactional
	public boolean deletePost(Integer postId, Integer userId) {

		if (Optional.ofNullable((getPost(postId))).isPresent()) {
			postsRepository.deleteById(postId);
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public long addLike(Integer postId, Integer userId) {

		var post = postMapper.transformBack(getPost(postId));
		post.incrementLikeCount();
		var user = userMapper.transformBack(userService.getUserByUserId(userId));

		var likeMapping = new Likes();
		likeMapping.setPosts(post);
		likeMapping.setUsers(user);
		try {
			likeRepository.save(likeMapping);
			postsRepository.save(post);
			return post.getLikeCount();
		} catch (Exception excp) {
			log.error("Cannot Save LIKES Entity");
			throw new ErrorSavingEntityToDatabaseException("Cannot Save to Database");
		}
	}

	@Override
	@Transactional
	public long removeLike(Integer postId, Integer userId) {

		var post = postMapper.transformBack(getPost(postId));
		post.decrementLikeCount();
		var user = userMapper.transformBack(userService.getUserByUserId(userId));

		try {
			likeRepository.deleteByPostsAndUsers(post, user);
			postsRepository.save(post);
			return post.getLikeCount();
		} catch (Exception excp) {
			log.error("Cannot Save LIKES Entity");
			throw new ErrorSavingEntityToDatabaseException("Cannot Save to Database");
		}
	}
}
