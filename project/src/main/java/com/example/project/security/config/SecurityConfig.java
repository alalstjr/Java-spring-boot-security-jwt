package com.example.project.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.project.security.filters.FormLoginFilter;
import com.example.project.security.hendlers.FormLoginAuthenticationFailuerHandler;
import com.example.project.security.hendlers.FormLoginAuthenticationSuccessHandler;
import com.example.project.security.providers.FormLoginAuthenticationProvider;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private FormLoginAuthenticationSuccessHandler formLoginAuthenticationSuccessHandler;
	private FormLoginAuthenticationFailuerHandler formLoginAuthenticationFailuerHandler;
	
	private FormLoginAuthenticationProvider provider;
	
	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}
	
	protected FormLoginFilter formLoginFilter() throws Exception {
		FormLoginFilter filter = new FormLoginFilter("/api/user/login", formLoginAuthenticationSuccessHandler, formLoginAuthenticationFailuerHandler);
		filter.setAuthenticationManager(super.authenticationManagerBean());
		
		return filter;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.authenticationProvider(this.provider);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf()
		.disable();
		
		http
		.headers()
		.frameOptions() 
		.disable();
		
		http
		.addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
