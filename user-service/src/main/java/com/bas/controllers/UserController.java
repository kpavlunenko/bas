package com.bas.controllers;

import com.bas.models.database.User;
import com.bas.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping
	public User save(@RequestBody User user) {
		user.setRole("general");
		return userService.save(user);
	}

}
