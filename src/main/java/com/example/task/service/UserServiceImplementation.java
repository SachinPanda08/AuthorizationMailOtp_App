package com.example.task.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.task.entity.Token;
import com.example.task.entity.User;
import com.example.task.repository.TokenRepository;
import com.example.task.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService{
	
@Autowired
UserRepository userRepository;

@Autowired
TokenRepository tokenRepository;

@Autowired
JavaMailSender mailSender;


@Override
public User findUserByUserName(String username) {
	return userRepository.findByUsername(username);
}


@Override
public void sendOtp(User user) {
     String otp = String.format("%06d", new Random().nextInt(999999));
     
     Token token = new Token();
     token.setUser(user);
     token.setOtp(otp);
     token.setCreatedAt(LocalDateTime.now());
     tokenRepository.save(token);
     
     SimpleMailMessage message = new SimpleMailMessage();
     message.setTo(user.getEmail());
     message.setSubject("Your Otp Code");
     message.setText("Your Otp code is: " + otp);
     mailSender.send(message);
}


@Override
public boolean verifyOtp(String otp) {
    Token token = tokenRepository.findByOtp(otp);
    if (token == null) {
    	return false;
    }
    if (ChronoUnit.MINUTES.between(token.getCreatedAt(), LocalDateTime.now()) > 1) {
        tokenRepository.delete(token);
        return false;
    }
    return true;
}
}
