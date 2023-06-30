package com.bas.services;

import com.bas.models.dto.AuthRequest;
import com.bas.models.dto.AuthResponse;
import com.bas.models.entities.UserVO;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
//@AllArgsConstructor
public class AuthService {

	private final RestTemplate restTemplate;
	private final JwtUtil jwtUtil;

	@Autowired
	public AuthService(RestTemplate restTemplate,
	                   final JwtUtil jwt) {
		this.restTemplate = restTemplate;
		this.jwtUtil = jwt;
	}

	public AuthResponse register(AuthRequest authRequest) {
		//TODO: add validation if user exist in db
		authRequest.setPassword(BCrypt.hashpw(authRequest.getPassword(), BCrypt.gensalt()));

		UserVO registeredUser = restTemplate.postForObject("http://user-service/users", authRequest, UserVO.class);

		String accessToken = jwtUtil.generate(registeredUser.getId().toString(), registeredUser.getRole(), "ACCESS");
		String refreshToken = jwtUtil.generate(registeredUser.getId().toString(), registeredUser.getRole(), "REFRESH");

		return new AuthResponse(accessToken, refreshToken);
	}

}
