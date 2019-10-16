package com.cg.code;

import java.math.BigDecimal;

public class WalletTest {

	public static void main(String[] args) {
		Service s = new Service();

		
		User u1 = new User("Asraf", "Hamid", "mdasraf@capgemini.com");
		Wallet w = s.createAccount(u1);
		Transaction t = s.topUp(w.getId(), new BigDecimal(100.00));
		System.out.println(t);
		
		
//		User u2 = new User("Benny", "Lava", "mdasraf@capgemini.com");
//		Wallet w2 = s.createAccount(u2);

		
//		System.out.println(s.repository.findWallet(0));
//		System.out.println(s.repository.findWalletByEmail("satan@capg.com"));

	}

}
