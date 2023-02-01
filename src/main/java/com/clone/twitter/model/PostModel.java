package com.clone.twitter.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class PostModel {

	private Integer id;
	private String text;
	private Integer userId;
	private List<String> images = new ArrayList<>(4);
	private Long likeCount;
	private Long repostCount;
	private Integer originalPostId;
	private Integer replyToId;
	private Date timestamp;
	private List<String> hashtags = new ArrayList<>();
	private List<String> mentions = new ArrayList<>();
}
