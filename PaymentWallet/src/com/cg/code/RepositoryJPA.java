package com.cg.code;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class RepositoryJPA implements Repository {

	private EntityManager em;

	public RepositoryJPA(EntityManager em) {
		this.em = em;
	}

	@Override
	public Wallet saveWallet(Wallet wallet) {

		em.getTransaction().begin(); // start

		em.persist(wallet.getTransaction());
		wallet.getTransactionMap().put(wallet.getTransaction().getId(), wallet.getTransaction());
		em.persist(wallet);

		em.getTransaction().commit();// end

		return wallet;
	}

	@Override
	public Wallet findWallet(int walletID) {

		return em.find(Wallet.class, walletID);
	}

	@Override
	public Wallet findWalletByEmail(String email){
		
		Wallet w = null;

	    Query query = em.createQuery("SELECT w FROM Wallet w JOIN w.user u WHERE u.email = :email"); 
	    query.setParameter("email", email);
	    
	    
	    try {
	        w = (Wallet) query.getSingleResult();
	    } catch (Exception e) {
	        return null;
	    }
	    
	    return w;
	}

}
