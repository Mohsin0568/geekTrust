package com.ledger.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

class LedgerTest {

	@Test
	void addNewLoanTest() {
		Ledger ledger = Ledger.getInstance();		
		assertNotNull(ledger);
		
		Loan testLoan = getLoanObject();
		
		boolean isAdded = ledger.addNewLoan(testLoan);
		assertTrue(isAdded);
		
		Optional<Loan> optionalObject = ledger.getLoan(testLoan.getCustomerName(), testLoan.getBankName());
		assertTrue(optionalObject.isPresent());
		
		Loan fetchedLoan = optionalObject.get();
		assertNotNull(fetchedLoan);
		assertEquals(testLoan.getCustomerName(), fetchedLoan.getCustomerName());
		assertEquals(testLoan.getBankName(), fetchedLoan.getBankName());
		assertEquals(testLoan.getPrincipalAmount(), fetchedLoan.getPrincipalAmount());
		assertEquals(testLoan.getTenureInYears(), fetchedLoan.getTenureInYears());
		assertEquals(testLoan.getInterestRate(), fetchedLoan.getInterestRate());
		
	}
	
	@Test
	void duplicateLoanShouldNotBeAddedToLedger() {
		
		Ledger ledger = Ledger.getInstance();		
		assertNotNull(ledger);
		
		Loan testLoan = getMelaLoanObject();
		
		boolean isAdded = ledger.addNewLoan(testLoan);
		assertTrue(isAdded);
		
		Loan duplicateLoan = getMelaLoanObject();
		boolean isDuplicateAdded = ledger.addNewLoan(duplicateLoan);
		assertFalse(isDuplicateAdded);
	}
	
	@Test
	void fetchLoanWhichDoesNotExistInLedger() {
		Ledger ledger = Ledger.getInstance();		
		assertNotNull(ledger);
		
		Optional<Loan> loan = ledger.getLoan("testCustomer", "testBank");
		assertFalse(loan.isPresent());
	}
	
	private Loan getLoanObject() {
		return new Loan("Dale", "IDIDI", 10000l, 1, 4);
	}
	
	private Loan getMelaLoanObject() {
		return new Loan("Mela", "ICICI", 1000l, 1, 4);
	}

}
