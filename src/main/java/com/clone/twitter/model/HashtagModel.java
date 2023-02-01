package com.clone.twitter.model;

import lombok.Data;

@Data
public class HashtagModel {

	private Integer id;
	private String tag;
	private Long recentPostCount;
}
