package com.bitcoin_investment.infrastructure.validation;

public class BitcoinAPIConnectionErrorException extends RuntimeException {
	private String systemTargetId;

	public BitcoinAPIConnectionErrorException() {
		this.systemTargetId = "www.mercadobitcoin.net";
	}

	public String getSystemTargetId() {
		return systemTargetId;
	}
}
