package com.cg.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import com.cg.code.*;
import com.cg.exception.*;

public class TestTransferAmount {

	Service s;
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
	EntityManager em = emf.createEntityManager();

	@Before
	public void init() {
		s = new Service(new RepositoryJPA(em));
	}

	@Test(expected = WalletNotExistException.class)
	public void notExistantWalletReceiver() {

		Wallet w = s.createAccount(new User("Asraf", "Hamid", "mdasraf@capg.com"));
		s.transfer(w.getId(), 99, new BigDecimal(50), "food");
	}

	@Test(expected = WalletNotExistException.class)
	public void notExistantWalletSender() {

		Wallet w = s.createAccount(new User("Billie", "Jean", "bj@capg.com"));
		s.transfer(99, w.getId(), new BigDecimal(50), "food");
	}

	@Test(expected = InvalidAmountException.class)
	public void transferLessThan0() {

		User u1 = new User("Charlie", "Brown", "cb@capg.com");
		User u2 = new User("Dick", "Tracy", "dick@capg.com");
		Wallet w1 = s.createAccount(u1);
		Wallet w2 = s.createAccount(u2);
		s.transfer(w1.getId(), w2.getId(), new BigDecimal(20.00).negate(), "loanshark");
	}

	@Test(expected = InvalidAmountException.class)
	public void transferBy0() {

		User u1 = new User("Be", "Bop", "bb@capg.com");
		User u2 = new User("Rock", "steady", "rs@capg.com");
		Wallet w1 = s.createAccount(u1);
		Wallet w2 = s.createAccount(u2);
		s.transfer(w1.getId(), w2.getId(), new BigDecimal(0.00), "loanshark");
	}

	@Test(expected = InsufficientFundsException.class)
	public void insufficientFunds() {

		User u1 = new User("Leonardo", "Raphael", "leonardo@capg.com");
		User u2 = new User("Donatello", "Michaelangelo", "donatello@capg.com");
		Wallet w1 = s.createAccount(u1);
		Wallet w2 = s.createAccount(u2);

		s.topUp(w1.getId(), new BigDecimal(10.00));

		s.transfer(w1.getId(), w2.getId(), new BigDecimal(20.00), "pizza");

	}

	@Test
	public void balanceCorrectlyUpdated() {

		User u1 = new User("Lonely", "Bun", "loo@capg.com");
		User u2 = new User("Benny", "Lava", "lava@capg.com");
		Wallet w1 = s.createAccount(u1);
		Wallet w2 = s.createAccount(u2);

		s.topUp(w1.getId(), new BigDecimal(100.00));

		Transaction t = s.transfer(w1.getId(), w2.getId(), new BigDecimal(20.00), "loanshark");

		// updated WALLET balance
		assertEquals(80.0, w1.getBalance().doubleValue(), 0.01);
		assertEquals(20.0, w2.getBalance().doubleValue(), 0.01);

	}
	/*
	 * transfer 
	 * ============== 
	 * 1)both wallet id should not be same (done) 
	 * 2)both wallet id must already exist (done) 
	 * 3)amount must be greater than 0 (done)
	 * 4)sender wallet must have sufficient funds to transfer amount 
	 * 5)balance must be correctly updated on both wallets (done)
	 */

}
