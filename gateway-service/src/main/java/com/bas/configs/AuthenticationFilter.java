package com.bas.configs;

import com.bas.services.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RefreshScope
public class AuthenticationFilter implements GatewayFilter {

	private final RouterValidator routerValidator;
	private final JwtUtil jwtUtil;

	public AuthenticationFilter(RouterValidator routerValidator, JwtUtil jwtUtil) {
		this.routerValidator = routerValidator;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();

		if (routerValidator.isSecured.test(request)) {
			if (authMissing(request))
				return onError(exchange, HttpStatus.UNAUTHORIZED);

			final String token = getAuthHeader(request);

			if (jwtUtil.isExpired(token))
				return onError(exchange, HttpStatus.UNAUTHORIZED);

			populateRequestWithHeaders(exchange, token);
		}
		return chain.filter(exchange);
	}

	private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		return response.setComplete();
	}

	private String getAuthHeader(ServerHttpRequest request) {
		return request.getHeaders().getOrEmpty("Authorization").get(0);
	}

	private boolean authMissing(ServerHttpRequest request) {
		return !request.getHeaders().containsKey("Authorization");
	}

	private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
		Claims claims = jwtUtil.getClaims(token);
		exchange.getRequest().mutate()
				.header("id", String.valueOf(claims.get("id")))
				.header("role", String.valueOf(claims.get("role")))
				.build();
	}

}
