package com.product.demo.config;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
public class PasswordGrantAuthenticationConverter implements AuthenticationConverter{

	 @Override
	    public Authentication convert(HttpServletRequest request) {
	        if (!"password".equals(request.getParameter("grant_type"))) {
	            return null;
	        }

	        String username = request.getParameter("username");
	        String password = request.getParameter("password");

	        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
	            throw new IllegalArgumentException("Username or password is missing");
	        }

	        Map<String, Object> additionalParameters = new HashMap<>();
	        additionalParameters.put("username", username);
	        additionalParameters.put("password", password);

	        return new PasswordGrantAuthenticationToken(null, username, password, additionalParameters);
	    }
}
