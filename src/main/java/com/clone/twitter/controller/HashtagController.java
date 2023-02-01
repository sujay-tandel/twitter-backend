package com.clone.twitter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.clone.twitter.model.HashtagModel;
import com.clone.twitter.model.PostModel;
import com.clone.twitter.service.HashtagService;

@RestController
public class HashtagController {

	@Autowired private HashtagService hashtagService;

	@GetMapping("/hashtags")
	public List<HashtagModel> getAllHashtags() {
		return hashtagService.getHashtags();
	}

	@GetMapping("/hashtag/{tag}/posts")
	public List<PostModel> getPosts(@PathVariable("tag") String tag) {
		return hashtagService.getPosts(tag);
	}
}
