package com.clone.twitter.service;

import java.util.List;

import com.clone.twitter.entity.Hashtags;
import com.clone.twitter.model.HashtagModel;
import com.clone.twitter.model.PostModel;

public interface HashtagService {

	public List<HashtagModel> getHashtags();

	public List<PostModel> getPosts(String tag);

	public List<Hashtags> getHashtagsByTags(List<String> hashtag);
}
