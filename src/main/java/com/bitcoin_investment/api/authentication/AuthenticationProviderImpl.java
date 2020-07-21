package com.bitcoin_investment.api.authentication;

import com.bitcoin_investment.core.command.ports.in.LoginCommand;
import com.bitcoin_investment.core.command.ports.in.LoginCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

	@Autowired
	private LoginCommandService loginCommandService;

	@Override
	public Authentication authenticate(Authentication authentication) {
		if (authentication.getName() == null || authentication.getCredentials() == null) {
			throw new InsufficientAuthenticationException("Invalid credentias (login or password nulls.)");
		}

		var loginCommand = new LoginCommand(authentication.getName(), authentication.getCredentials().toString());
		if (!loginCommandService.isCredentialAuthentic(loginCommand)) {
			throw new BadCredentialsException("Invalid credentials.");
		}

		return new UsernamePasswordAuthenticationToken(authentication.getName(),
				authentication.getCredentials().toString(), Collections.emptyList());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}


}
