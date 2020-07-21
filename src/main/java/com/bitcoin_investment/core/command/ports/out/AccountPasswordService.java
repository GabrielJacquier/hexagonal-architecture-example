package com.bitcoin_investment.core.command.ports.out;

public interface AccountPasswordService {

	boolean matchesPasswords(String rawPassword, String encryptedPassword);

}
