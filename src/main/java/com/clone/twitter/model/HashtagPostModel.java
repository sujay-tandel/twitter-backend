package com.clone.twitter.model;


import lombok.Data;

@Data
public class HashtagPostModel {

	private Integer id;
	private HashtagModel hashtag;
	private PostModel post;
}
