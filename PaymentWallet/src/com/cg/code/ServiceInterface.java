package com.cg.code;

import java.math.BigDecimal;

public interface ServiceInterface {

	Wallet createAccount(User user);
	Transaction topUp(int walletID, BigDecimal amount);
	BigDecimal showBalance(int walletID);
	Transaction transfer(int senderWalletID,
			int receiverWalletID,BigDecimal amount,String desc);
}
