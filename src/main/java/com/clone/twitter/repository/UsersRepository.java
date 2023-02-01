package com.clone.twitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clone.twitter.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {
	public Users findByUsername(String username);
}
