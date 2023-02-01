package com.clone.twitter.model;


import lombok.Data;

@Data
public class UserModel {

	private Integer id;
	private String username;
	private String name;
	private String bio;
	private Long followerCount;
	private Long followingCount;
	private Boolean verified;
}
