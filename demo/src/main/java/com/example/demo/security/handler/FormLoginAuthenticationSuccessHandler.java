package com.example.demo.security.handler;

import com.example.demo.dto.TokenDTO;
import com.example.demo.model.Account;
import com.example.demo.security.jwt.JwtFactory;
import com.example.demo.security.token.PostAuthorizationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    // 2.
    private final JwtFactory   factory;
    private final ObjectMapper objectMapper;

    // 1.
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest req,
            HttpServletResponse res,
            Authentication auth
    ) throws IOException {

        PostAuthorizationToken token   = (PostAuthorizationToken) auth;
        UserDetails            userDetails = token.getUserDetails();

        // 2.
        String tokenString = factory.generateToken(userDetails);

        // 3.
        TokenDTO tokenDTO = new TokenDTO(tokenString, userDetails.getUsername());

        processResponse(res, tokenDTO);
    }

    private void processResponse(
            HttpServletResponse res,
            TokenDTO dto
    ) throws IOException {
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setStatus(HttpStatus.OK.value());
        res.getWriter().write(objectMapper.writeValueAsString(dto));
    }
}