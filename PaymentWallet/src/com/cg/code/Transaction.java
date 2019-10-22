package com.cg.code;

import java.math.BigDecimal;

import java.util.Date;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="transaction")
public class Transaction {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id=0;

	private BigDecimal amount;
	private BigDecimal balance;
	private String description;
	private Timestamp date;
	
	public Transaction(int id, BigDecimal a, BigDecimal b, String desc, Timestamp timestamp){
		this.amount= a;
		this.balance = b;
		this.description= desc;
		this.date = timestamp;
	}
	
	//transfer fund transaction
	public Transaction(BigDecimal amount, BigDecimal balance, String description){ 

		this.amount= amount;
		this.balance = balance;
		this.description= description;
		this.date = new Timestamp(new Date().getTime());
	}
	public Transaction() {
		
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
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
	public Timestamp getDate() {
		return date;
	}
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", amount=" + amount
				+ ", balance=" + balance + ", description=" + description + ", date=" + date + "]";
	}
	
}
