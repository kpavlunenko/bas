package com.bas.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private String expiration;

	public Claims getClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
				.build()
				.parseClaimsJwt(token)
				.getBody();
	}

	public Date getExpirationDate (String token) {
		return getClaims(token).getExpiration();
	}

	public String generate(String userId, String role, String tokenType) {
		Map<String, String> claims = Map.of("id", userId, "role", role);
		long expMillis = "ACCESS".equalsIgnoreCase(tokenType)
				? Long.parseLong(expiration) * 1000
				: Long.parseLong(expiration) * 1000 * 5;

		Date now = new Date();
		Date exp = new Date(now.getTime() * expMillis);
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(claims.get("id"))
				.setIssuedAt(now)
				.setExpiration(exp)
				.signWith(Keys.hmacShaKeyFor(secret.getBytes()))
				.compact();
	}

	private boolean isExpired(String token) {
		return getExpirationDate(token).before(new Date());
	}

}
