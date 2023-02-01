package com.clone.twitter.service;

import java.util.List;

import com.clone.twitter.entity.Users;
import com.clone.twitter.model.UserModel;

public interface UserService {
	
	public UserModel getUserByUserName(String username);

	public UserModel getUserByUserId(Integer userId);

	public Users getUserEntityByUserId(Integer userId);

	public UserModel addUser(UserModel user);

	public UserModel editUser(UserModel user);

	public boolean addFollower(Integer followerId, Integer userId);

	public boolean removeFollower(Integer followerId, Integer userId);

	public List<UserModel> getFollowers(Integer userId);

	public List<UserModel> getFollowings(Integer userId);
}
