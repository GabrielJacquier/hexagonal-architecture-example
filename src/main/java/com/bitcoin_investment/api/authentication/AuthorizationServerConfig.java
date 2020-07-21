package com.bitcoin_investment.api.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Value("${oauth2.client_id}")
	private String clientId;
	
	@Value("${oauth2.client_secret}")
	private String clientSecret;
	
	@Value("${oauth2.access_token_seconds}")
	private Integer accessTokenSeconds;
	
	@Value("${oauth2.refresh_token_seconds}")
	private Integer refreshTokenSeconds;

	@Value("${jwt.key}")
	private String jwtSigningKey;

	static final String GRANT_TYPE_KEY = "password";
	static final String AUTHORIZATION_CODE = "authorization_code";
	static final String REFRESH_TOKEN = "refresh_token";
	static final String IMPLICIT = "implicit";
	static final String SCOPE_READ = "read";
	static final String SCOPE_WRITE = "write";
	static final String TRUST = "trust";

	@Autowired
	private AuthenticationManager authenticationManager;

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(jwtSigningKey);
		return converter;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer configure) throws Exception {
		configure.inMemory()
			.withClient(clientId)
			.secret(clientSecret)
			.authorizedGrantTypes(GRANT_TYPE_KEY, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT)
			.scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
			.accessTokenValiditySeconds(accessTokenSeconds)
			.refreshTokenValiditySeconds(refreshTokenSeconds);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore())
			.authenticationManager(authenticationManager)
			.accessTokenConverter(accessTokenConverter());
	}

}