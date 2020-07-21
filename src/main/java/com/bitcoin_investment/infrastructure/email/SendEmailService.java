package com.bitcoin_investment.infrastructure.email;

import com.bitcoin_investment.core.command.ports.out.Message;
import com.bitcoin_investment.core.command.ports.out.MessageSender;
import com.bitcoin_investment.infrastructure.persistence.AccountPersistence;
import com.bitcoin_investment.infrastructure.validation.PersistenceNotFoundErrorException;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService implements MessageSender {
	private Logger logger = LoggerFactory.getLogger(SendEmailService.class);

	@Value("${email.api_key}")
	private String apiKey;

	@Value("${email.secret_key}")
	private String secretKey;

	@Value("${email.enabled}")
	private boolean sendEmailEnabled;

	@Autowired
	private AccountPersistence accountPersistence;

	@Override
	public void sendMessage(Message message) {
		if (this.sendEmailEnabled) {
			var recipientAccount = accountPersistence.findById(message.getRecipientId().getValue())
					.orElseThrow(PersistenceNotFoundErrorException::new);

			this.sendEmail(message.getSubject(), message.getBody(),
					recipientAccount.getEmail(), recipientAccount.getName());
		}
	}

	private void sendEmail(String subject, String bodyMessage, String recipientEmail, String recipientName) {
		try {
			MailjetClient client = new MailjetClient(apiKey, secretKey, new ClientOptions("v3.1"));

			MailjetRequest request = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES, new JSONArray()
				.put(new JSONObject()
					.put(Emailv31.Message.FROM, createJsonObjectFromSender())
					.put(Emailv31.Message.TO, new JSONArray().put(createJsonObjectFromRecipient(recipientEmail, recipientName)))
					.put(Emailv31.Message.SUBJECT, subject)
					.put(Emailv31.Message.TEXTPART, bodyMessage)
					.put(Emailv31.Message.HTMLPART, "<h3>" + bodyMessage + "</h3>")
					.put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")
				));

			client.post(request);
			logger.info(String.format("Email send to %s", recipientEmail));
		} catch (MailjetSocketTimeoutException | MailjetException e) {
			logger.error(e.getMessage(), e);
		}
	}

	private JSONObject createJsonObjectFromSender() {
		return new JSONObject()
				.put("Email", "investment.btc@gmail.com")
				.put("Name", "Bitcoin Investment");
	}

	private JSONObject createJsonObjectFromRecipient(String recipientEmail, String recipientName) {
		return new JSONObject()
				.put("Email", recipientEmail)
				.put("Name", recipientName);
	}
}
