package com.cg.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.cg.code.*;
import com.cg.exception.*;


public class TestCreateAccount {

	Service s;
	Repository repo = new RepositoryCollection();
	@Before
	public void init() {
		s = new Service(repo);
	}
	@Test
	public void newUserTest() {
		User u = new User("Charlie", "Brown", "cb@capgemini.com");
		Wallet w = s.createAccount(u);
		assertTrue(w instanceof Wallet);
		assertEquals("Charlie", w.getUser().getFname());
		assertEquals("Brown", w.getUser().getLname());
		assertEquals("cb@capgemini.com", w.getUser().getEmail());
	}
	@Test (expected = EmailAlreadyRegisteredException.class)
	public void duplicateEmailTest() {
		//create 2 account with same email address
		User u1 = new User("Asraf", "Hamid", "mdasraf@capg.com");
		User u2 = new User("Billie", "Jean", "mdasraf@capg.com");
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
		User u = new User("Dick", "Tracy", "dick@capgemini..com");
		s.createAccount(u);
	}
	

}
