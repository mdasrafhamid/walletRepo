package com.cg.code;

import java.math.BigDecimal;

import com.cg.exception.*;

public class Service implements ServiceInterface {

	private Repository repository;

	public Service(Repository repository) {
		this.repository = repository;
	}

	@Override
	public Wallet createAccount(User user) {

		// check if any of field in user object is NULL
		if (user.getFname() == null || user.getLname() == null || user.getEmail() == null) {
			throw new NullNameEmailUserException();
		}
		// check if email syntax is correct
		if (!Utility.checkEmailSyntax(user.getEmail())) {
			throw new WrongEmailSyntaxException();
		}

		// check if wallet exist with a given email
		Wallet testWallet = null;
		try {
			testWallet = repository.findWalletByEmail(user.getEmail());
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		if (testWallet != null) {
			System.out.println("User with this email exists! : " + user.getEmail());
			throw new EmailAlreadyRegisteredException();
		}

		Transaction t = new Transaction(new BigDecimal(0.00), new BigDecimal(0.00), user.getFname() + " first wallet!");
		Wallet w = new Wallet(user, t);

		try {
			repository.saveWallet(w);
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return w;

	}

	@Override
	public Transaction topUp(int walletID, BigDecimal amount) {

		Wallet w;
		try {
			w = repository.findWallet(walletID);
		} catch (Exception e) {
			System.out.println("wallet exception");
			w = null;
			e.printStackTrace();
		}

		// check if wallet exist
		if (w == null)
			throw new WalletNotExistException();

		// check if amount less than | equal zero
		if (amount.compareTo(new BigDecimal(0.00)) != 1)
			throw new InvalidAmountException();

		Transaction t = new Transaction(amount, w.getBalance().add(amount), "Top up of " + amount + "!");
		w.setTransaction(t);
		w.setBalance(t.getBalance());


		try {
			repository.saveWallet(w);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return t;
	}

	@Override
	public Transaction showBalance(int walletID) {
		Wallet w = null;
		try {
			w = repository.findWallet(walletID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// check if wallet exist
		if (w == null)
			throw new WalletNotExistException();

		Transaction t = new Transaction(new BigDecimal(0.00), w.getBalance(), "Show balance!");
		w.setTransaction(t);

		try {
			repository.saveWallet(w);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return t;
	}

	@Override
	public Transaction transfer(int senderWalletID, int receiverWalletID, BigDecimal amount, String desc) {

		Wallet senderW = null, receiverW = null;

		// check if both IDs are the same
		if (senderWalletID == receiverWalletID)
			throw new SameWalletTransferException();

		try {
			senderW = repository.findWallet(senderWalletID);
			receiverW = repository.findWallet(receiverWalletID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// check if both wallets exist
		if (senderW == null || receiverW == null)
			throw new WalletNotExistException();

		// check if amount less than | equal zero
		if (amount.compareTo(new BigDecimal(0.00)) != 1)
			throw new InvalidAmountException();

		// check if sender has sufficient funds
		if (amount.compareTo(senderW.getBalance())==1)
			throw new InsufficientFundsException();
		
		Transaction tS = new Transaction(amount, senderW.getBalance().add(amount.negate()),
				"Send $" + amount + " to " + receiverW.getUser().getFname() + ", " + desc);
		senderW.setTransaction(tS);
		senderW.setBalance(tS.getBalance());

		Transaction tR = new Transaction(amount, receiverW.getBalance().add(amount),
				"Receive $" + amount + " from " + senderW.getUser().getFname() + ", " + desc);
		receiverW.setTransaction(tR);
		receiverW.setBalance(tR.getBalance());

		try {
			repository.saveWallet(senderW);
			repository.saveWallet(receiverW);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return tS;
	}

}
