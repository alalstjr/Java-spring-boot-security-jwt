package com.example.demo.security.token;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken {

    // 1.
    private PostAuthorizationToken(
            Object principal,
            Object credentials,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(principal, credentials, authorities);
    }

    public static PostAuthorizationToken getTokenFormUserDetails(UserDetails userDetails) {

        return new PostAuthorizationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
    }

    public UserDetails getUserDetails() {

        return (UserDetails) super.getPrincipal();
    }
}