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

public class TestTopUpWallet {

	Service s;
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
	EntityManager em = emf.createEntityManager();

	@Before
	public void init() {
		s = new Service(new RepositoryJPA(em));
	}

	@Test(expected = WalletNotExistException.class)
	public void notExistantWallet() {

		s.topUp(9, new BigDecimal(10.00));
	}

	@Test(expected = InvalidAmountException.class)
	public void topUpLessThan0() {

		User u1 = new User("Charlie", "Brown", "cb@capg.com");
		Wallet w = s.createAccount(u1);
		s.topUp(w.getId(), new BigDecimal(10.00).negate());
	}

	@Test(expected = InvalidAmountException.class)
	public void topUpBy0() {

		User u1 = new User("Asraf", "Hamid", "mdasraf@capgemini.com");
		Wallet w = s.createAccount(u1);
		s.topUp(w.getId(), new BigDecimal(0.00));
	}

	@Test
	public void balanceCorrectlyUpdated() {
		User u1 = new User("Billie", "Jean", "bj@capgemini.com");
		Wallet w = s.createAccount(u1);

		Transaction t = s.topUp(w.getId(), new BigDecimal(10.00));
		// updated WALLET balance
		assertEquals(10.0, w.getBalance().doubleValue(), 0.01);

		t = s.topUp(w.getId(), new BigDecimal(55.00));
		// updated TRANSACTION balance
		assertEquals(65.0, t.getBalance().doubleValue(), 0.01);
	}
	/*
	 * top up wallet 
	 * ============= 
	 * 1)wallet id must exist (done) 
	 * 2)top up must be
	 * greater than 0 (done) 
	 * 3)balance on wallet and transaction added correctly(done) 
	 * 4)can topup a wallet multiple times (done)
	 */

}
