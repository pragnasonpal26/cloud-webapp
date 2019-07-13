package com.neu.csye6225.webApplication;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.neu.csye6225.webApplication.entity.User;
import com.neu.csye6225.webApplication.repository.UserRepository;
import com.neu.csye6225.webApplication.service.UserService;

import static org.junit.Assert.*;

public class UserServiceTest {


    @Mock
    UserService userService;

    @Mock
    UserRepository userRepository;


    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);


    }

    @Test
    public void save() {

        User user = new User();
        user.setEmail("neha@gmail.com");
        user.setPassword("neha123");
        userRepository.save(user);

    }
}
