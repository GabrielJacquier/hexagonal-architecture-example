package com.bitcoin_investment.infrastructure.persistence;

import com.bitcoin_investment.core.command.ports.out.AccountPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountPasswordCryptService implements AccountPasswordService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public String encode(String password) {
		return passwordEncoder.encode(password);
	}

	@Override
	public boolean matchesPasswords(String rawPassword, String encryptedPassword) {
		return passwordEncoder.matches(rawPassword, encryptedPassword);
	}
}
