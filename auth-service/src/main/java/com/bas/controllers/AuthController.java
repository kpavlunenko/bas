package com.bas.controllers;

import com.bas.models.dto.AuthRequest;
import com.bas.models.dto.AuthResponse;
import com.bas.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping(value = "/register")
	public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest authRequest) {
		return ResponseEntity.ok(authService.register(authRequest));
	}

}
