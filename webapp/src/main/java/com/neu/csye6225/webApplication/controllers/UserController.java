package com.neu.csye6225.webApplication.controllers;



//import com.neu.csye6225.webApplication.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.neu.csye6225.webApplication.model.User;
//import com.neu.csye6225.webApplication.service.UserService;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.Valid;

import java.sql.Timestamp;


@Controller
public class UserController {

 @Autowired
 //private UserServiceImpl userService;
 
  
Date date= new Date();
 
 long time = date.getTime();
     
 
 @GetMapping(value= {"/"})
 public String login() {
  return "Hi";
 }
 
 @RequestMapping(value= {"/signup"}, method=RequestMethod.POST)
 public User createUser(@Valid User user) {
  
  User userExists = userService.findUserByEmail(user.getEmail());
  
  if(userExists != null) {
   System.out.println("email"+"This email already exists!");
   return user;
  }
  else
  {
   userService.saveUser(user);
   System.out.println("email"+"Successfully registered");
   return user;
  }
 
 }
 
 
 
}
