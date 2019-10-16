package com.cg.code;

public interface RepositoryInterface {

	Wallet saveWallet(Wallet wallet);
	Wallet findWallet(int walletID);
	Wallet findWalletByEmail(String email);
}
