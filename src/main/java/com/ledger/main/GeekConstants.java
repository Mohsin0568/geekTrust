/**
 * 
 */
package com.ledger.main;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author mohsin
 *
 */
public class GeekConstants {

	public static final String LOAN_STR = "LOAN";
	public static final String PAYMENT_STR = "PAYMENT";
	public static final String BALANCE_STR = "BALANCE";
	
	public static void main(String args[]) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		System.out.println(Math.round(3.14f));
		System.out.println(Math.round(3.54f));
		System.out.println(Math.round(3.44f));
		System.out.println(Math.round(3.74f));
	}
}
