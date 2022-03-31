/**
 * 
 */
package com.ledger.main;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author mohsin
 *
 */

public class Ledger {

	private Map<Long, Loan> ledger;
	
	private static Ledger ledgerInstance;
	
	private Ledger() {
		ledger = new HashMap<>();
	}
	
	static Ledger getInstance() {
		if(ledgerInstance == null) {
			synchronized (Ledger.class) {
				if(ledgerInstance == null) {
					ledgerInstance = new Ledger();
				}
			}
		}
		
		return ledgerInstance;
	}
	
	public boolean addNewLoan(Loan loan) {
		
		Long primaryKey = 0l;
		
		try {
			primaryKey = getUniqueKey(loan.getCustomerName(), loan.getBankName());
		}		
		catch(NoSuchAlgorithmException | UnsupportedEncodingException e) {
			return false;
		}
		
		// check if already record exists in Ledger for given customerName and bankName, if exists then we will not add loan to the ledger
		
		Loan alreadyExistedLoan = ledger.get(primaryKey);		
		if(alreadyExistedLoan != null)
			return false;
		
		// add Loan to ledger
		ledger.put(primaryKey, loan);		
		return true;
	}
	
	public Optional<Loan> getLoan(String customerName, String bankName) {
		Long primaryKey = 0l;
		
		try {
			primaryKey = getUniqueKey(customerName, bankName);
		}		
		catch(NoSuchAlgorithmException | UnsupportedEncodingException e) {
			return Optional.empty();
		}
		
		Loan customerLoan = ledger.get(primaryKey);
		if(customerLoan == null)
			return Optional.empty();
		else
			return Optional.of(customerLoan);
	}
	
	private Long getUniqueKey(String customerName, String bankName) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		MessageDigest md = MessageDigest.getInstance("MD5");
		String key = customerName + bankName;
		md.update(key.getBytes(StandardCharsets.UTF_8));
		return (long) new BigInteger(1, md.digest()).intValue();
	}
}
