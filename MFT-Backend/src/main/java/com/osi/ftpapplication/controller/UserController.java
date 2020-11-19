package com.osi.ftpapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osi.ftpapplication.exception.InvalidUserException;
import com.osi.ftpapplication.exception.UsernameNotFoundException;
import com.osi.ftpapplication.model.User;
import com.osi.ftpapplication.service.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	/**
	 * Validating user with userName
	 * 
	 * @param username
	 * @return only if it is a valid user
	 * @throws InvalidUserException
	 * @throws UsernameNotFoundException
	 */

	@GetMapping("/validUser/{username}/{password}")
	public User isValidUser(@PathVariable String username,
			@PathVariable String password) throws InvalidUserException, UsernameNotFoundException {
		return userService.isValidUser(username, password);
	}

	/**
	 * Get All Active Users
	 * 
	 * @return all users in DB
	 */
	@GetMapping("/getAllUser")
	public List<User> getAllUser() {
		return userService.getAllUser();
	}
}