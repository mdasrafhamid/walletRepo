package com.cg.test;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.cg.code.RepositoryJPA;
import com.cg.code.Service;
import com.cg.code.Transaction;
import com.cg.code.User;
import com.cg.code.Wallet;

public class WalletTest {

	public static void main(String[] args) {
		
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager em = emf.createEntityManager();
		Service s = new Service(new RepositoryJPA(em));
		
//		Service s = new Service(new RepositoryJDBC());
		
		User u1 = new User("Asraf", "Hamid", "md@capg.com");
		Wallet w1 = s.createAccount(u1);
		
		Transaction t = s.topUp(w1.getId(), new BigDecimal(100.00));
		
		User u2 = new User("Benny", "Lava", "beejay@capg.com");
		Wallet w2 = s.createAccount(u2);
		
		System.out.println(s.showBalance(1));
		
		s.topUp(2, new BigDecimal(50.00));
		s.topUp(1, new BigDecimal(10.00));
		
		s.transfer(1, 2, new BigDecimal(25.00), "food");
		
//		User u1 = new User("Edward", "Park", "eddy@capg.com");	
//		User u2 = new User("Fandi", "ahmad", "bola@capg.com");
//		
//		Wallet w1 = s.createAccount(u1);
//		Wallet w2 = s.createAccount(u2);
//		
//		System.out.println(w2);
	}

}
