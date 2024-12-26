package com.example.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.example.task.entity.Token;
@Repository
@EnableJpaRepositories
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Token findByOtp(String otp); 
}
