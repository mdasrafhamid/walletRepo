package com.cg.code;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class RepositoryCollection implements Repository{
	private Map<Integer,Wallet> walletMap = new HashMap<Integer, Wallet>();

	@Override
	public Wallet saveWallet(Wallet wallet) {
		
		walletMap.put(wallet.getId(), wallet);
		return wallet;
	}

	@Override
	public Wallet findWallet(int walletID) {
		
		return walletMap.get(walletID);
	}
	@Override
	public Wallet findWalletByEmail(String email) {
		
		
		for (Entry<Integer, Wallet> entry : walletMap.entrySet()) {
			if(email.equals(entry.getValue().getUser().getEmail())){
				return entry.getValue();
			}
		}
		
		return null;
		
		
	}

	
	
}
