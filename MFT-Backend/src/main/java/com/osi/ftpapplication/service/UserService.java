package com.osi.ftpapplication.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osi.ftpapplication.exception.InvalidUserException;
import com.osi.ftpapplication.exception.UsernameNotFoundException;
import com.osi.ftpapplication.model.User;
import com.osi.ftpapplication.repository.UserRepository;


@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public User isValidUser(String username, String password) throws InvalidUserException, UsernameNotFoundException {
		Optional<User> user = userRepository.findByUserName(username);
		User ans = user.get();
		if(ans.getPassword().equalsIgnoreCase(password))
			return ans;
		else
			throw new InvalidUserException("The given user is invalid"); 
	}
	
	public Optional<User> findByUsername(String username) {
		Optional<User> user = userRepository.findByUserName(username);
		return user;
	}
	
	public List<User> getAllUser(){
		return userRepository.findAll();
		
	}

}
