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

public class TestCheckBalance {

	Service s;
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
	EntityManager em = emf.createEntityManager();

	@Before
	public void init() {
		s = new Service(new RepositoryJPA(em));
	}

	@Test(expected = WalletNotExistException.class)
	public void notExistantWallet() {
		// wallet with id 0 does not exist
		s.showBalance(0);
	}

	@Test
	public void balanceCorrectlyUpdated() {
		User u1 = new User("Billie", "Jean", "bj@capg.com");
		Wallet w = s.createAccount(u1);

		s.topUp(w.getId(), new BigDecimal(10.00));
		// s.showBalance(walletID)
		assertEquals(10.0, s.showBalance(w.getId()).getBalance().doubleValue(), 0.01);

		s.topUp(w.getId(), new BigDecimal(55.00));
		assertEquals(65.0, s.showBalance(w.getId()).getBalance().doubleValue(), 0.01);
	}
	/*
	 * show balance
	 * ==============
	 * 1)wallet id must exist (done) 
	 * 2)balance on returned transaction must be correct (done)
	 */

}
