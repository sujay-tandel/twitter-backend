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
@Table(name = "likes")
@Data
public class Likes {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@ManyToOne(targetEntity = Posts.class)
	@JoinColumn(name = "posts_id")
	private Posts posts;

	@ManyToOne(targetEntity = Users.class)
	@JoinColumn(name = "users_id")
	private Users users;

	@CreationTimestamp private Date createdAt;

	@UpdateTimestamp private Date updatedAt;
}
