package com.product.demo.config;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Map;
public class PasswordGrantAuthenticationProvider implements AuthenticationProvider{
	 private final UserDetailsService userDetailsService;

	    public PasswordGrantAuthenticationProvider(UserDetailsService userDetailsService) {
	        this.userDetailsService = userDetailsService;
	    }

	    @Override
	    public Authentication authenticate(Authentication authentication) {
	        if (!(authentication instanceof PasswordGrantAuthenticationToken token)) {
	            return null;
	        }

	        String username = token.getPrincipal().toString();
	        String password = token.getCredentials().toString();

	        UserDetails user = userDetailsService.loadUserByUsername(username);
	        if (user == null || !user.getPassword().equals(password)) {
	            throw new RuntimeException("Invalid username or password");
	        }

	        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
	    }

	    @Override
	    public boolean supports(Class<?> authentication) {
	        return PasswordGrantAuthenticationToken.class.isAssignableFrom(authentication);
	    }
}
