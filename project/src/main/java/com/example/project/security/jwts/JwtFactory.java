package com.example.project.security.jwts;

import java.io.UnsupportedEncodingException;
import java.sql.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.project.domain.Account;

@Component
public class JwtFactory {

	private static final Logger log = LoggerFactory.getLogger(JwtFactory.class);
	
	private static String signingKey = "jwttest";
	
	public String generateToken(Account account) {
		String token = null;
		try {
			token = JWT.create() 
					.withIssuer("jjunpro")
					.withClaim("USERNAME", account.getUserId())
					// 반환값 string 으로 변호나해줘야함
					.withClaim("USER_ROLE", account.getUserRole())
					.withClaim("EXP", new Date(System.currentTimeMillis() + 864000000))
					.sign(generateAlgorithm());
		} catch(Exception e) {
			log.error(e.getMessage());
		}
		
		return token;
	}
	
	private Algorithm generateAlgorithm() throws UnsupportedEncodingException {
		return Algorithm.HMAC256(signingKey);
	}

}