package com.clone.twitter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clone.twitter.entity.Users;
import com.clone.twitter.model.UserModel;
import com.clone.twitter.service.UserService;
//import com.clone.twitter.utility.Utility;

import lombok.extern.slf4j.Slf4j;

@RestController	
@RequestMapping("/users")
@Slf4j
public class UserController {

	@Autowired private UserService userService;

//	@Autowired private Utility utility;

	@GetMapping("/{userNameOrUserId}")
	public ResponseEntity<UserModel> getUserByUserIdOrUserName(
			@PathVariable("userNameOrUserId") String userNameOrUserId) {

		UserModel userResponse;

		if (userNameOrUserId.startsWith("@")) {
			log.info("input resource is a username");
			var username = userNameOrUserId.substring(1);
			userResponse = userService.getUserByUserName(username);
			return new ResponseEntity<>(userResponse, HttpStatus.OK);
		}

		log.info("input resource is a Integer");
		Integer userId = Integer.parseInt(userNameOrUserId);
		userResponse = userService.getUserByUserId(userId);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<UserModel> createUser(@RequestBody UserModel userResponse) {
		var user = userService.addUser(userResponse);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

	@PatchMapping
	public UserModel updateUser(@RequestBody UserModel userResponse, @AuthenticationPrincipal Users user) {
		return userService.editUser(userResponse);
	}

	@PutMapping("/{userId}/follow")
	public ResponseEntity<HttpStatus> addFollower(@PathVariable Integer userId, @AuthenticationPrincipal Users user) {
		
		userService.addFollower(userId, user.getId()); 
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@DeleteMapping("/{userId}/follow")
	public ResponseEntity<HttpStatus> removeFollower(
			@PathVariable("userId") Integer userId, @AuthenticationPrincipal Users user) {
		userService.removeFollower(userId, user.getId()); // TODO: Extract from Principal
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/{userId}/followers")
	public List<UserModel> getFollowers(@PathVariable("userId") Integer userId) {
		return userService.getFollowers(userId);
	}

	@GetMapping("/{userId}/followings")
	public List<UserModel> getFollowings(@PathVariable("userId") Integer userId) {
		return userService.getFollowings(userId);
	}
}
