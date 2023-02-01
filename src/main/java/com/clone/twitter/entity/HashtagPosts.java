package com.clone.twitter.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "hashtag_posts")
@Data
public class HashtagPosts {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@ManyToOne(targetEntity = Hashtags.class)
	@JoinColumn(name = "hashtags_id")
	private Hashtags hashtags;

	@ManyToOne(targetEntity = Posts.class)
	@JoinColumn(name = "posts_id")
	private Posts posts;

	@CreationTimestamp private Date createdAt;

	@UpdateTimestamp private Date updatedAt;
}
