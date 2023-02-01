package com.clone.twitter.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.clone.twitter.entity.Posts;
import com.clone.twitter.model.PostModel;

@Component("PostMapper")
public class PostMapper implements Mapper<Posts, PostModel> {

	@Override
	public PostModel transform(Posts post) {
		PostModel postModel = new PostModel();
		BeanUtils.copyProperties(post, postModel, "hashtags", "mentions");
		postModel.setHashtags(new ArrayList<>(post.getHashtags().keySet()));
		postModel.setMentions(new ArrayList<>(post.getMentions().keySet()));
		postModel.setUserId(post.getUsers().getId());
		return postModel;
	}

	@Override
	public Posts transformBack(PostModel postModel) {
		Posts post = new Posts();
		BeanUtils.copyProperties(postModel, post, "hashtags", "mentions", "likeCount", "repostCount");
		Map<String, Date> hashtags = new HashMap<>();
		Map<String, Date> mentions = new HashMap<>();
		postModel.getHashtags().forEach(tag -> hashtags.put(tag, new Date()));
		postModel.getMentions().forEach(mention -> mentions.put(mention, new Date()));
		post.setHashtags(hashtags);
		post.setMentions(mentions);
		post.setLikeCount(null != postModel.getLikeCount() ? postModel.getLikeCount() : 0L);
		post.setRepostCount(null != postModel.getRepostCount() ? postModel.getRepostCount() : 0L);
		return post;
	}
}
