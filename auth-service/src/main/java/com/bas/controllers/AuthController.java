package com.bas.controllers;

import com.bas.models.dto.AuthRequest;
import com.bas.models.dto.AuthResponse;
import com.bas.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
//@AllArgsConstructor
public class AuthController {

	private final AuthService authService;

	@Autowired
	public AuthController(final AuthService authService) {
		this.authService = authService;
	}

	@PostMapping(value = "/register")
	public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest authRequest) {
		return ResponseEntity.ok(authService.register(authRequest));
	}

}
