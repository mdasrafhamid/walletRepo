package com.cg.code;

public interface Repository {

	Wallet saveWallet(Wallet wallet) throws Exception;
	Wallet findWallet(int walletID) throws Exception;
	Wallet findWalletByEmail(String email) throws Exception;
}
