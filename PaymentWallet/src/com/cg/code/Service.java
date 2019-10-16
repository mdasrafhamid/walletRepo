package com.cg.code;

import java.math.BigDecimal;

import com.cg.exception.EmailAlreadyRegisteredException;
import com.cg.exception.NullNameEmailUserException;
import com.cg.exception.WrongEmailSyntaxException;


public class Service implements ServiceInterface {

	private RepositoryInterface repository = new Repository();

	@Override
	public Wallet createAccount(User user) {
		
		// check if any of field in user object is NULL
		if (user.getFname() == null || user.getLname() == null || user.getEmail() == null) {
			throw new NullNameEmailUserException();
		}
		//check if email syntax is correct
		if(!Utility.checkEmailSyntax(user.getEmail())) {
			throw new WrongEmailSyntaxException();
		}
		
		// check if wallet exist with a given email
		Wallet testWallet = repository.findWalletByEmail(user.getEmail());
		if (testWallet != null) {
			System.out.println("User with this email exists! : " + user.getEmail());
			throw new EmailAlreadyRegisteredException();
		}
		
		Transaction t = new Transaction();
		Wallet w = new Wallet(user, t);

		return repository.saveWallet(w);
	}

	@Override
	public Transaction topUp(int walletID, BigDecimal amount) {
		
		Wallet w = repository.findWallet(walletID);
		Transaction t = new Transaction(null, w, amount, w.getBalance().add(amount),"top up "+amount);
		w.getTransactionMap().put(walletID, t);
		
		return t;
	}

	@Override
	public BigDecimal showBalance(int walletID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transaction transfer(int senderWalletID, int receiverWalletID, BigDecimal amount, String desc) {
		// TODO Auto-generated method stub
		return null;
	}

}
