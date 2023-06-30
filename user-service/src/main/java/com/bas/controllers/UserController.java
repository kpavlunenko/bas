package com.bas.controllers;

import com.bas.models.database.User;
import com.bas.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<User> save(@RequestBody User user) {
		user.setRole("general");
		return ResponseEntity.ok(userService.save(user));
	}

	@GetMapping("/secured")
	public ResponseEntity<String> securedEndpoint() {
		return ResponseEntity.ok("test");
	}

}
