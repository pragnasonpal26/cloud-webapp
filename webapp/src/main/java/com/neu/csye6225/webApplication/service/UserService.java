package com.neu.csye6225.webApplication.service;

import com.amazonaws.services.sns.AmazonSNSAsync;
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.neu.csye6225.webApplication.entity.User;
import com.neu.csye6225.webApplication.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class UserService {

	AmazonSNSAsync amazonSNSClient;

	private final static Logger logger = LoggerFactory.getLogger(UserService.class);
	@PostConstruct
	public void initializeSNSClient() {

		this.amazonSNSClient = AmazonSNSAsyncClientBuilder.defaultClient();
	}

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

	public String resetPassword(String email){
		User existing = userRepository.findByEmail(email);
		String message = "";
		if(existing == null)
			message = "EmailId not found";
		else{
			try {
				message = sendMessage(email);
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return message;
	}

	private String sendMessage(String emailId) throws ExecutionException, InterruptedException {
		logger.info("Sending Message to topic--- {} ", emailId);
		String topicArn = getTopicArn("password_reset");
		PublishRequest publishRequest = new PublishRequest(topicArn, emailId);
		Future<PublishResult> publishResultFuture = amazonSNSClient.publishAsync(publishRequest);
		String messageId = publishResultFuture.get().getMessageId();
		String message = "Sent Message " + emailId + " with message Id " + messageId;
		logger.info(message);
		return message;
	}

	public String getTopicArn(String topicName) {

		String topicArn = null;

		try {
			Topic topic = amazonSNSClient.listTopicsAsync().get().getTopics().stream()
					.filter(t -> t.getTopicArn().contains(topicName))
					.findAny()
					.orElse(null);

			if (null != topic) {
				topicArn = topic.getTopicArn();
			} else {
				logger.info("No Topic found by the name ---> ", topicName);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		logger.info("Arn corresponding to topic name {} is {} ", topicName, topicArn);

		return topicArn;

	}

}
