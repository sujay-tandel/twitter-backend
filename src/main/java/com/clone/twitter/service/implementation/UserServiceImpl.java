package com.clone.twitter.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clone.twitter.entity.Users;
import com.clone.twitter.model.UserModel;
import com.clone.twitter.repository.UsersRepository;
import com.clone.twitter.service.UserService;
import com.clone.twitter.utility.Mapper;

@Service
public class UserServiceImpl implements UserService {

	@Autowired private UsersRepository usersRepository;

	@Autowired
	@Qualifier("UserMapper")
	private Mapper<Users, UserModel> userMapper;

	@Override
	public UserModel getUserByUserName(String username) {
		return userMapper.transform(usersRepository.findByUsername(username));
	}

	@Override
	public UserModel getUserByUserId(Integer userId) {
		var user = usersRepository.findById(userId).get();
		return userMapper.transform(user);
	}

	@Override
	public Users getUserEntityByUserId(Integer userId) {
		return usersRepository.findById(userId).get();
	}

	@Override
	@Transactional
	public UserModel addUser(UserModel user) {
		Users users = userMapper.transformBack(user);
		return userMapper.transform(usersRepository.save(users));
	}

	@Override
	@Transactional
	public UserModel editUser(UserModel user) {
		Users users = userMapper.transformBack(user);
		return userMapper.transform(usersRepository.save(users));
	}

	@Override
	@Transactional
	public boolean addFollower(Integer followerId, Integer userId) {
		Users user = usersRepository.findById(userId).get();
		user.setFollower(followerId);
		usersRepository.save(user);
		return true;
	}

	@Override
	public boolean removeFollower(Integer followerId, Integer userId) {
		Users user = usersRepository.findById(userId).get();
		user.removeFollower(followerId);
		usersRepository.save(user);
		return true;
	}

	@Override
	public List<UserModel> getFollowers(Integer userId) {
		List<UserModel> followers = new ArrayList<>();
		Users user = usersRepository.findById(userId).get();
		List<Users> users = usersRepository.findAllById(user.getFollower().keySet());
		Optional.ofNullable(users)
		.ifPresent(
				usersList ->
				usersList.forEach(eachUser -> followers.add(userMapper.transform(eachUser))));
		return followers;
	}

	@Override
	public List<UserModel> getFollowings(Integer userId) {
		List<UserModel> followings = new ArrayList<>();
		Users user = usersRepository.findById(userId).get();
		List<Users> users = usersRepository.findAllById(user.getFollowing().keySet());
		Optional.ofNullable(users)
		.ifPresent(
				usersList ->
				usersList.forEach(eachUser -> followings.add(userMapper.transform(eachUser))));
		return followings;
	}
}
