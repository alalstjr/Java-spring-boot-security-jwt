package com.example.demo.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.model.Account;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtFactory {

    private static final Logger log = LoggerFactory.getLogger(JwtFactory.class);

    // 1.
    public String generateToken(UserDetails userDetails) {
        String token = null;
        try {
            Set<String> roles = userDetails.getAuthorities().stream()
                    .map(r -> r.getAuthority()).collect(Collectors.toSet());
            String role = roles.iterator().next();

            token = JWT.create()
                    .withIssuer("JJUNPRO")
                    .withClaim("USERNAME", userDetails.getUsername())
                    .withClaim("USER_ROLE", role)
                    .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)))
                    .sign(generateAlgorithm());
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return token;
    }

    // 2.
    private Algorithm generateAlgorithm() throws UnsupportedEncodingException {
        String signingKey = "jwttest";
        return Algorithm.HMAC256(signingKey);
    }
}
