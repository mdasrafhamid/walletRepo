package com.cg.code;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
	private static int c=0;
	
	private int id;
	private Wallet sender;
	private Wallet receiver;
	private BigDecimal amount;
	private BigDecimal balance;
	private String description;
	private Date date;
	
	
	public Transaction(){ //create FIRST wallet
		this.id = c++;
		this.sender = null;
		this.receiver = null;
		this.amount= new BigDecimal("0.00");
		this.balance = new BigDecimal("0.00");
		this.description= "New Wallet created!";
		this.date = new Date();
	}
	
	//transfer fund transaction
	public Transaction(Wallet sender, Wallet receiver, BigDecimal amount, BigDecimal balance, String description){ 
		this.id = c++;
		this.sender = sender;
		this.receiver = receiver;
		this.amount= amount;
		this.balance = balance;
		this.description= description;
		this.date = new Date();
	}
	
	public int getId() {
		return id;
	}
	public Wallet getSender() {
		return sender;
	}
	public Wallet getReceiver() {
		return receiver;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public String getDescription() {
		return description;
	}
	public Date getDate() {
		return date;
	}
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", amount=" + amount
				+ ", balance=" + balance + ", description=" + description + ", date=" + date + "]";
	}
	
}
