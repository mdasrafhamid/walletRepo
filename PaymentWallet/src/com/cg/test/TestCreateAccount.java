package com.cg.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.cg.code.*;
import com.cg.exception.*;


public class TestCreateAccount {

	Service s;
	
	@Before
	public void init() {
		s = new Service();
	}
	
	@Test
	public void newUserTest() {
		
		User u = new User("Asraf", "Hamid", "mdasraf@capgemini.com");
		Wallet w = s.createAccount(u);
		assertTrue(w instanceof Wallet);
		assertEquals("Asraf", w.getUser().getFname());
		assertEquals("Hamid", w.getUser().getLname());
		assertEquals("mdasraf@capgemini.com", w.getUser().getEmail());
	}
	
	@Test (expected = EmailAlreadyRegisteredException.class)
	public void duplicateEmailTest() {
		
		User u1 = new User("Asraf", "Hamid", "mdasraf@capgemini.com");
		User u2 = new User("Benny", "Lava", "mdasraf@capgemini.com");
		s.createAccount(u1);
		s.createAccount(u2);
		
	}
	@Test (expected = NullNameEmailUserException.class)
	public void nullUserNameEmailTest() {
		
		User u = new User(null, "Hamid", null);
		s.createAccount(u);
	}
	@Test (expected = WrongEmailSyntaxException.class)
	public void wrongUserEmailSyntax() {
		
		User u = new User("Asraf", "Hamid", "mdasraf@capgemini..com");
		s.createAccount(u);
	}
	

}
