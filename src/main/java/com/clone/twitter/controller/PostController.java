package com.clone.twitter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clone.twitter.service.PostService;
import com.clone.twitter.entity.Users;
import com.clone.twitter.model.PostModel;

@RestController
@RequestMapping("/posts")
public class PostController {

	@Autowired private PostService postService;

	@GetMapping
	public ResponseEntity<List<PostModel>> getAllPosts() {
		List<PostModel> posts = postService.getAllPosts();
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	@GetMapping("/{postId}")
	public ResponseEntity<PostModel> getPost(@PathVariable("postId") Integer postId) {
		PostModel post = postService.getPost(postId);
		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<PostModel> addPost(@RequestBody PostModel postModel) {
		PostModel post = postService.addPost(postModel);
		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@DeleteMapping("/{postId}")
	public ResponseEntity<HttpStatus> deletePost(
			@PathVariable("postId") Integer postId, @AuthenticationPrincipal Users user) {

		Integer userId = 1; // TODO: Extract from Principal
		if(user.getId() == postService.getPost(postId).getUserId()) {
			postService.deletePost(postId, userId);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/{postId}/like")
	public ResponseEntity<Long> likePost(@PathVariable("postId") Integer postId, @AuthenticationPrincipal Users user) {

		if(user.getId() == postService.getPost(postId).getUserId()) {
			return new ResponseEntity<>(postService.addLike(postId, user.getId()), HttpStatus.CREATED);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/{postId}/like")
	public ResponseEntity<Long> removeLikePost(
			@PathVariable("postId") Integer postId, @AuthenticationPrincipal Users user) {

		if(user.getId() == postService.getPost(postId).getUserId()) {
			return new ResponseEntity<>(postService.removeLike(postId, user.getId()), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
