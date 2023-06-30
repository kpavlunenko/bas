package com.bas.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	public Claims getClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
				.build()
				.parseClaimsJwt(token)
				.getBody();
	}

	public boolean isExpired(String token) {
		return getClaims(token).getExpiration().before(new Date());
	}


}
