package com.example.task.service;

import com.example.task.entity.User;

public interface UserService {
	User findUserByUserName(String username);
	
	public void sendOtp(User user) ;
	
	public boolean verifyOtp(String otp);
}
