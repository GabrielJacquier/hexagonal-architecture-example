package com.bitcoin_investment.core.command.ports.out;

import com.bitcoin_investment.core.command.domain.entity.account.AccountId;

public class Message {
	private AccountId recipientId;
	private String subject;
	private String body;

	public Message(AccountId recipientId, String subject, String body) {
		this.recipientId = recipientId;
		this.subject = subject;
		this.body = body;
	}

	public AccountId getRecipientId() {
		return recipientId;
	}

	public String getSubject() {
		return subject;
	}

	public String getBody() {
		return body;
	}

}
