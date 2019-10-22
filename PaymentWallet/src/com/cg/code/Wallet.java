package com.cg.code;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;

@Entity
@Table(name="wallet")
public class Wallet {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	private int id = -1;
	
	private BigDecimal balance;
	
	@OneToMany(cascade = {CascadeType.ALL},orphanRemoval = true)
	@JoinColumn(name = "wallet_id")
	private Map<Integer,Transaction> transactionMap = new HashMap<Integer, Transaction>();
	
	@OneToOne(cascade = {CascadeType.ALL})
	private User user;
	
	@Transient
	private Transaction temp;
	
	public Wallet(User user,Transaction transaction) {

		this.balance=new BigDecimal("0.00");
		this.user=user;
		this.temp = transaction;
		
	}

	public Wallet() {
		
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
	public Transaction getTransaction() {
		return temp;
	}
	public void setTransaction(Transaction temp) {
		this.temp = temp;
	}
	@Override
	public String toString() {
		return "Wallet [id=" + id + ", balance=" + balance + ", transactionMap=" + transactionMap + ", user=" + user + "]";
	}
	
	
}
