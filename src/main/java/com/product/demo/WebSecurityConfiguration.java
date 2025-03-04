package com.product.demo;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.cors.CorsUtils;


@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(
						auth -> auth.requestMatchers(CorsUtils::isCorsRequest).permitAll().anyRequest().authenticated())
				.httpBasic(httpBasic -> httpBasic.disable()) // If you want basic auth, remove .disable()
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(new WebSecurityCorsFilter(), ChannelProcessingFilter.class);

		return http.build();
	}
}

/*
 * 
 * @EnableGlobalMethodSecurity(prePostEnabled = true)
 * 
 * @Order(Ordered.LOWEST_PRECEDENCE) public class WebSecurityConfiguration
 * extends WebSecurityConfigurerAdapter {
 * 
 * @Override protected void configure(HttpSecurity http) throws Exception { http
 * .authorizeRequests() .requestMatchers(CorsUtils::isCorsRequest).permitAll()
 * .anyRequest().authenticated() .and().httpBasic() .and().addFilterBefore(new
 * WebSecurityCorsFilter(), ChannelProcessingFilter.class); }
 */