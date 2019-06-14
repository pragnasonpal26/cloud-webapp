package com.neu.csye6225.webApplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.neu.csye6225.webApplication.entity.User;
import com.neu.csye6225.webApplication.service.UserService;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public String create(@RequestBody User user) {
		String message = userService.create(user);
		if(message == "User already exists" || message == "Password length should be greater than 1" )
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
		return message;
	}
}
