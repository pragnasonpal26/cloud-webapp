package com.neu.csye6225.webApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.neu.csye6225.webApplication.entity.Books;
import com.neu.csye6225.webApplication.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
 

 String findQuery = "select email, password from user where email=?";
	@Query(value = findQuery, nativeQuery = true)
	User findByEmail(String email);
}

