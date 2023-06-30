package com.bas.services;

import com.bas.models.dto.AuthRequest;
import com.bas.models.dto.AuthResponse;
import com.bas.models.entities.User;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class AuthService {

	private final RestTemplate restTemplate;
	private final JwtUtil jwtUtil;

	public AuthResponse register(AuthRequest authRequest) {
		//TODO: add validation if user exist in db
		authRequest.setPassword(BCrypt.hashpw(authRequest.getPassword(), BCrypt.gensalt()));

		User registeredUser = restTemplate.postForObject("http://user-service/users", authRequest, User.class);

		String accessToken = jwtUtil.generate(registeredUser.getId().toString(), registeredUser.getRole(), "ACCESS");
		String refreshToken = jwtUtil.generate(registeredUser.getId().toString(), registeredUser.getRole(), "REFRESH");

		return new AuthResponse(accessToken, refreshToken);
	}

}
