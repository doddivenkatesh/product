package com.product.demo.config;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
public class PasswordGrantAuthenticationToken  extends AbstractAuthenticationToken {
	
	private static final long serialVersionUID = 1L;
	private final Authentication clientPrincipal;
	    private final String username;
	    private final String password;
	    private final Map<String, Object> additionalParameters;

	    public PasswordGrantAuthenticationToken(Authentication clientPrincipal, String username, String password, Map<String, Object> additionalParameters) {
	        super(Collections.emptyList());
	        this.clientPrincipal = clientPrincipal;
	        this.username = username;
	        this.password = password;
	        this.additionalParameters = additionalParameters;
	        setAuthenticated(false);
	    }

	    @Override
	    public Object getCredentials() {
	        return password;
	    }

	    @Override
	    public Object getPrincipal() {
	        return username;
	    }

	    public Authentication getClientPrincipal() {
	        return clientPrincipal;
	    }

	    public AuthorizationGrantType getGrantType() {
	        return new AuthorizationGrantType("password");
	    }

	    public Map<String, Object> getAdditionalParameters() {
	        return additionalParameters;
	    }
}
