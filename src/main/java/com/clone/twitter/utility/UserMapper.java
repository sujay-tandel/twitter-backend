package com.clone.twitter.utility;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.clone.twitter.entity.Users;
import com.clone.twitter.model.UserModel;

@Component("UserMapper")
public class UserMapper implements Mapper<Users, UserModel> {

	@Override
	public UserModel transform(Users user) {
		var userModel = new UserModel();
		BeanUtils.copyProperties(user, userModel);
		return userModel;
	}

	@Override
	public Users transformBack(UserModel userModel) {
		var user = new Users();
		BeanUtils.copyProperties(userModel, user, "followerCount", "followingCount", "verified");
		user.setFollowerCount(userModel.getFollowerCount() != null ? userModel.getFollowerCount() : 0L);
		user.setFollowingCount(
				userModel.getFollowingCount() != null ? userModel.getFollowingCount() : 0L);
		user.setVerified(userModel.getVerified() != null ? userModel.getVerified() : false);
		return user;
	}
}
