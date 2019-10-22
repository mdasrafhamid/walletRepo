package com.cg.code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

public class RepositoryJDBC implements Repository {

	@Override
	public Wallet saveWallet(Wallet w) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/sakila?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
				"root", "Capgemini123");

		Statement st = con.createStatement();
		String query;
		ResultSet rs;
		PreparedStatement pStmt;

		// if new record --> save user, wallet
		if (w.getId() == -1) {
			query = "insert into user (first_name, last_name, email) values (?, ?, ?)";
			pStmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			pStmt.setString(1, w.getUser().getFname());
			pStmt.setString(2, w.getUser().getLname());
			pStmt.setString(3, w.getUser().getEmail());
			pStmt.execute();

			rs = pStmt.getGeneratedKeys();
			rs.next();

			w.getUser().setId(rs.getInt(1));

			query = "INSERT INTO wallet (u_id, balance) VALUES (?, ?)";
			pStmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			pStmt.setInt(1, w.getUser().getId());
			pStmt.setBigDecimal(2, w.getBalance());
			pStmt.execute();

			rs = pStmt.getGeneratedKeys();
			rs.next();

			w.setId(rs.getInt(1));

		} else { //existing user --> update wallet balance

			query = "UPDATE wallet set balance = ? where wallet_id = ?";
			
			pStmt = con.prepareStatement(query);
			pStmt.setBigDecimal(1, w.getBalance());
			pStmt.setInt(2, w.getId());
			pStmt.execute();

		}
		
		//insert latest transaction
		query = "insert into transaction (w_id, amount, balance, description, date) values (?, ?, ?, ?, ?)";
		pStmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		pStmt.setInt(1, w.getId());
		pStmt.setBigDecimal(2, w.getTransaction().getAmount());
		pStmt.setBigDecimal(3, w.getTransaction().getBalance());
		pStmt.setString(4, w.getTransaction().getDescription());
		pStmt.setTimestamp(5, w.getTransaction().getDate());
		pStmt.execute();

		rs = pStmt.getGeneratedKeys();
		rs.next();
		
		w.getTransaction().setId(rs.getInt(1));
		w.getTransactionMap().put(rs.getInt(1), w.getTransaction());
		

		con.close();

		return w;
	}

	@Override
	public Wallet findWallet(int walletID) throws Exception {

		Wallet w = new Wallet();
		User u = new User();

		w.setId(walletID);
		w.setUser(u);

		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/sakila?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
				"root", "Capgemini123");

		Statement st = con.createStatement();

		String stmnt = "select * from wallet where wallet_id = " + walletID;
		ResultSet rs = st.executeQuery(stmnt);

		while (rs.next()) {
			u.setId(rs.getInt("u_id"));
			w.setBalance(rs.getBigDecimal("balance"));
		}

		stmnt = "select * from user where user_id = " + u.getId();
		rs = st.executeQuery(stmnt);

		while (rs.next()) {
			u.setFname(rs.getString("first_name"));
			u.setLname(rs.getString("last_name"));
			u.setEmail(rs.getString("email"));
		}

		stmnt = "select * from transaction where w_id = " + walletID;
		rs = st.executeQuery(stmnt);

		while (rs.next()) {

			Transaction t = new Transaction(rs.getInt("t_id"), rs.getBigDecimal("amount"), rs.getBigDecimal("balance"),
					rs.getString("description"),rs.getTimestamp("date"));
			w.getTransactionMap().put(t.getId(), t);
		}
		con.close();
		return w;
	}

	@Override
	public Wallet findWalletByEmail(String email) throws Exception {
		Wallet w = new Wallet();
		User u = new User();

		w.setUser(u);

		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/sakila?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
				"root", "Capgemini123");

		Statement st = con.createStatement();

		String stmnt = "select * from user where email = '" + email + "'";

		ResultSet rs = st.executeQuery(stmnt);

		if (rs.next() == false) // no user with email exist
			return null;

		while (rs.next()) {
			u.setId(rs.getInt("user_id"));
			u.setFname(rs.getString("first_name"));
			u.setLname(rs.getString("last_name"));
			u.setEmail(rs.getString("email"));
		}

		stmnt = "select * from wallet where u_id = " + u.getId();
		rs = st.executeQuery(stmnt);

		while (rs.next()) {
			w.setId(rs.getInt("wallet_id"));
			w.setId(rs.getInt("u_id"));
			w.setBalance(rs.getBigDecimal("balance"));
		}

		stmnt = "select * from transaction where w_id = " + w.getId();
		rs = st.executeQuery(stmnt);

		while (rs.next()) {

			Transaction t = new Transaction(rs.getInt("t_id"), rs.getBigDecimal("amount"), rs.getBigDecimal("balance"),
					rs.getString("description"), rs.getObject("date",Timestamp.class));
			w.getTransactionMap().put(t.getId(), t);
		}
		con.close();
		return w;
	}


}
