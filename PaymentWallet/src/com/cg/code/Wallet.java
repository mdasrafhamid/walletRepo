package com.cg.code;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Wallet {
	private static int c=0;
	
	private int id;
	private BigDecimal balance;
	private Map<Integer,Transaction> transactionMap = new HashMap<Integer, Transaction>();
	private User user;
	
	public Wallet(User user,Transaction transaction) {
		this.id = c++;
		this.balance=new BigDecimal("0.00");
		this.user=user;
		this.transactionMap.put(transaction.getId(), transaction);
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public Map<Integer,Transaction> getTransactionMap() {
		return transactionMap;
	}
	public void setTransactionMap(Map<Integer,Transaction> transactionMap) {
		this.transactionMap = transactionMap;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Wallet [id=" + id + ", balance=" + balance + ", transactionMap=" + transactionMap + ", user=" + user
				+ "]";
	}
	
	
}
