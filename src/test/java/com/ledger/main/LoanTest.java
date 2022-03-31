package com.ledger.main;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LoanTest {

	@Test
	void test() {
		
		Loan daleLoan = getDaleLoanObject();
		Loan harryLoan = getHarryLoanObject();
		Loan shellyLoan = getShellyLoanObject();
		
		daleLoan.addPayments(Payment.builder().emiNumber(5).lumsumAmount(1000l).build());
		harryLoan.addPayments(Payment.builder().emiNumber(10).lumsumAmount(5000l).build());
		shellyLoan.addPayments(Payment.builder().emiNumber(12).lumsumAmount(7000l).build());
		
		String daleEnquiry = daleLoan.enquiry(3);
		assertEquals("1326 9", daleEnquiry);
		
		daleEnquiry = daleLoan.enquiry(6);
		assertEquals("3652 4", daleEnquiry);
		
		String harryEnquiry = harryLoan.enquiry(12);
		assertEquals("9032 10", harryEnquiry);
		
		String shellyEnquiry = shellyLoan.enquiry(12);
		assertEquals("15856 3", shellyEnquiry);
	}

	private Loan getDaleLoanObject() {
		
		return new Loan("Dale", "IDIDI", 5000l, 1, 6f);
		
	}
	
	private Loan getHarryLoanObject() {
		
		return new Loan("Harry", "MBI", 10000l, 3, 7f);
		
	}
	
	private Loan getShellyLoanObject() {
		
		return new Loan("Shelly", "UON", 15000l, 2, 9f);
		
	}
}
