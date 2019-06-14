package com.neu.csye6225.webApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.neu.csye6225.webApplication.entity.User;
import com.neu.csye6225.webApplication.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public String create(User user) {
		User existing = userRepository.findByEmail(user.getEmail());
		if(existing != null)
			return "User already exists";
		String password = user.getPassword();
		if(password.length() <= 1)
			return "Password length should be greater than 1";
		String encryptedPassword = passwordEncoder.encode(password);
		user.setPassword(encryptedPassword);
		User newuser = userRepository.save(user);
		return ("Username " + newuser.getEmail() + " registered");
	}
}
