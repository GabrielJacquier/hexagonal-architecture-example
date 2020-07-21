package com.bitcoin_investment.core.command.ports.out;

import com.bitcoin_investment.core.command.domain.entity.bitcoin.BitcoinStore;

public interface BitcoinStoreCommandRepository {

	BitcoinStore getStore();

}
