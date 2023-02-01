package com.clone.twitter.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "posts")
@Data
public class Posts {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "text")
	private String text;

	@ManyToOne
	@JoinColumn(name = "users_id")
	@CreatedBy
	private Users users;

	@ElementCollection private Map<String, Date> images = new HashMap<>(4); // maximum of 4 images

	@Column(name = "like_count")
	private Long likeCount = 0L;

	@Column(name = "repost_count")
	private Long repostCount = 0L;

	@Column(name = "orig_post_id")
	private Integer originalPostId;

	@Column(name = "reply_to_id")
	private Integer replyToId;

	@CreationTimestamp private Date timestamp;

	@UpdateTimestamp private Date updatedAt;

	@ElementCollection private Map<String, Date> hashtags = new HashMap<>();

	@ElementCollection private Map<String, Date> mentions = new HashMap<>();

	@OneToMany(mappedBy = "posts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<HashtagPosts> postHashtags = new ArrayList<>();

	@OneToMany(mappedBy = "posts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Likes> postLikes = new ArrayList<>();

	public long incrementLikeCount() {
		return ++likeCount;
	}

	public long decrementLikeCount() {
		return (likeCount < 1) ? 0 : --likeCount;
	}

	public long incrementRepostCount() {
		return ++repostCount;
	}

	public long decrementRepostCount() {
		return (repostCount < 1) ? 0 : --repostCount;
	}

}
