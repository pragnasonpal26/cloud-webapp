package com.neu.csye6225.webApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neu.csye6225.webApplication.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmail(String email);
	
}
