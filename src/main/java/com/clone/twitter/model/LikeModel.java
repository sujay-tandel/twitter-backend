package com.clone.twitter.model;

import lombok.Data;

@Data
public class LikeModel {

	private Integer id;
	private PostModel post;
	private UserModel user;
}
