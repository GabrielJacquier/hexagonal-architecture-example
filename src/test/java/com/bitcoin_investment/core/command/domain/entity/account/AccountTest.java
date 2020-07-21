package com.bitcoin_investment.core.command.domain.entity.account;

import com.bitcoin_investment.core.command.domain.entity.bitcoin.BitcoinInvestment;
import com.bitcoin_investment.core.command.domain.validation.DomainValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AccountTest {

	@Test
	public void successIncrementAccountBalanceAfterDepositsAreDone() {
		Account account = new Account("Test", "teste@teste.com");
		account.doDeposit(new RealAmount(BigDecimal.valueOf(20.00)));
		account.doDeposit(new RealAmount(BigDecimal.valueOf(20.00)));
		account.doDeposit(new RealAmount(BigDecimal.valueOf(20.45)));

		assertEquals(BigDecimal.valueOf(60.45), account.getBalance().getValue());
	}

	@Test
	public void failDepositForNegativeAmount() {
		Account account = new Account("Test", "teste@teste.com");
		Assertions.assertThrows(DomainValidation.class, () -> account.doDeposit(new RealAmount(BigDecimal.valueOf(-20.00))));
	}

	@Test
	public void failWithdrawAmountBiggerThanBalanceAvailable() {
		Account account = new Account("Test", "teste@teste.com");
		account.doDeposit(new RealAmount(BigDecimal.valueOf(20.00)));

		Assertions.assertThrows(DomainValidation.class, () -> account.doWithdraw(new RealAmount(BigDecimal.valueOf(30.00))));
	}

	@Test
	public void successSubtractAccountBalanceWhenWithdraw() {
		Account account = new Account("Test", "teste@teste.com");
		account.doDeposit(new RealAmount(BigDecimal.valueOf(20.00)));
		account.doWithdraw(new RealAmount(BigDecimal.valueOf(15.55)));

		assertEquals(BigDecimal.valueOf(4.45), account.getBalance().getValue());
	}

	@Test
	public void successSubtractPurchasedInvestmentsWhenSale() {
		Account account = new Account("Test", "teste@teste.com");
		var investmentToSale = new BitcoinInvestment(BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN);
		account.purchaseInvestment(investmentToSale);
		account.purchaseInvestment(new BitcoinInvestment(BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN));
		account.purchaseInvestment(new BitcoinInvestment(BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN));

		account.saleInvestment(investmentToSale);

		assertEquals(2, account.getPurchasedInvestments().size());
	}

}
