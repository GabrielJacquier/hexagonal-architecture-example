package com.bitcoin_investment.core.command.service;

import com.bitcoin_investment.core.command.domain.entity.bitcoin.BitcoinInvestment;
import com.bitcoin_investment.core.command.service.event.AccountDepositDone;
import com.bitcoin_investment.core.command.service.event.AccountPurchaseDone;
import com.bitcoin_investment.core.command.service.event.AccountSaleDone;
import com.bitcoin_investment.core.command.ports.out.Message;
import com.bitcoin_investment.core.command.ports.out.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@Service
public class MessageEventListenerService {
	private Logger logger = LoggerFactory.getLogger(MessageEventListenerService.class);

	@Autowired
	private MessageSender messageSender;

	@EventListener
	@Async
	public void sendMessageWhenNewDepositIsDone(AccountDepositDone accountDepositDone) {
		String valueFormatted = new DecimalFormat("'R$' #0.00")
				.format(accountDepositDone.getValueDeposited().getValue().doubleValue());

		messageSender.sendMessage(new Message(accountDepositDone.getAccountId(),
				"Successful Deposit!",
				"Thank you for your deposit of %s.".replace("%s", valueFormatted)));

		logger.info("Message sent.");
	}

	@EventListener
	@Async
	public void sendMessageWhenNewPurchaseIsDone(AccountPurchaseDone accountPurchaseDone) {
		String valueFormatted = new DecimalFormat("'R$' #0.00")
				.format(accountPurchaseDone.getValuePurchased().getValue().doubleValue());

		String btcAmountFormatted = new DecimalFormat("#0.0####################")
				.format(accountPurchaseDone.getBitcoinAmount().setScale(20, RoundingMode.DOWN));

		messageSender.sendMessage(new Message(accountPurchaseDone.getAccountId(),
				"Successful Purchase!",
				"Thank you for your purchase, was invested: %v and purchase %btc Bitcoin(s)."
						.replace("%v", valueFormatted)
						.replace("%btc", btcAmountFormatted)));

		logger.info("Message sent.");
	}

	@EventListener
	@Async
	public void sendMessageWhenSaleIsDone(AccountSaleDone accountSaleDone) {
		String valueFormatted = new DecimalFormat("'R$' #0.00")
				.format(accountSaleDone.getValueRescue().getValue().doubleValue());

		var btcSoldAmount = accountSaleDone.getInvestmentsSold().stream()
				.map(BitcoinInvestment::getBitcoinAmount)
				.mapToDouble(BigDecimal::doubleValue)
				.sum();

		String btcAmountFormatted = new DecimalFormat("#0.0####################")
				.format(btcSoldAmount);

		messageSender.sendMessage(new Message(accountSaleDone.getAccountId(),
				"Successful Sale!",
				"Thank you for your sale, was rescued: %v and sold %btc Bitcoin(s)."
						.replace("%v", valueFormatted)
						.replace("%btc", btcAmountFormatted)));

		logger.info("Message sent.");
	}

}
