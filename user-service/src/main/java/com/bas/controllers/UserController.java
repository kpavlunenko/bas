package com.bas.controllers;

import com.bas.models.database.User;
import com.bas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
//@AllArgsConstructor
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public User save(@RequestBody User user) {
		user.setRole("general");
		return userService.save(user);
	}

}
