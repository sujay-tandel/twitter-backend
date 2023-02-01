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
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "username")
	private String username;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "bio")
	private String bio;

	@Column(name = "follower_count")
	private long followerCount = 0L;

	@Column(name = "following_count")
	private Long followingCount = 0L;

	@Column(name = "verified")
	private Boolean verified = false;

	@CreationTimestamp private Date createdAt;

	@UpdateTimestamp private Date updatedAt;

	@OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Likes> userLikes = new ArrayList<>();

	@ElementCollection private Map<Integer, Date> follower = new HashMap<>();

	@ElementCollection private Map<Integer, Date> following = new HashMap<>();

	@OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Posts> userPosts = new ArrayList<>();

	public void setFollower(final Integer userId) {
		follower.put(userId, new Date());
	}

	public void setFollowing(final Integer userId) {
		following.put(userId, new Date());
	}

	public void removeFollower(final Integer userId) {
		follower.remove(userId);
	}

	public void removeFollowing(final Integer userId) {
		following.remove(userId);
	}
}
