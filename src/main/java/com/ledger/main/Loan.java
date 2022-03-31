package com.ledger.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import lombok.Getter;
import lombok.ToString;

/**
 * @author mohsin
 *
 */

@Getter
@ToString
public class Loan {
	
	private String customerName;
	private String bankName;
	private Long principalAmount;
	private int tenureInYears;
	private float interestRate;
	private double totalAmount;
	private List<Long> emis;
	private Long emiAmount;
	private Map<Integer, Payment> payments;
	
	
	public Loan(String customerName, String bankName, Long principalAmount, int tenureInYears, float interestRate) {
		super();
		this.customerName = customerName;
		this.bankName = bankName;
		this.principalAmount = principalAmount;
		this.tenureInYears = tenureInYears;
		this.interestRate = interestRate;
		this.totalAmount = (long) (principalAmount + ((principalAmount * tenureInYears * interestRate)/100));
		emis = new ArrayList<>();
		payments = new HashMap<>();
		calculateEmis();
	}
	
	public String enquiry(int enquiryTillEmi) {
		// getting total lumsum amount paid till enquiry emi
		AtomicLong totalLumsumPaid = new AtomicLong(0l);
		payments.forEach((k, v) -> {
			if(k <= enquiryTillEmi) {
				totalLumsumPaid.addAndGet(v.getLumsumAmount());
			}
		});
		
		// getting total amount paid		
		long totalAmountPaid = this.emis.stream().limit(enquiryTillEmi).reduce(0l, (s1, s2) -> s1 + s2);
		
		// reducing number of emi's based on totalLumsumPaid
		int numberOfEmisReduced = 0;
		if(totalLumsumPaid.get() > 0) {
			numberOfEmisReduced = (int) (totalLumsumPaid.get() / emiAmount);
		}
		
		Long amountPending = totalAmountPaid + totalLumsumPaid.get();
		int emisPending = emis.size() - enquiryTillEmi - numberOfEmisReduced;
		
		StringBuilder enquiryDetails = new StringBuilder();
		enquiryDetails.append(amountPending);
		enquiryDetails.append(" ");
		enquiryDetails.append(emisPending);
		
		return enquiryDetails.toString();
		
	}
	
	private void calculateEmis() {
		
		emiAmount = (long) Math.round(this.totalAmount/(this.tenureInYears*12));
		int totalMonths = this.tenureInYears * 12;
		for(int i = 1 ; i <= totalMonths; i++) {
			if(i != totalMonths) {
				emis.add(emiAmount);
			}
			else { // adjusting last emi with remaining amount
				long lastEmiAmount = (long) (totalAmount - (emiAmount * (totalMonths - 1)));
				emis.add(lastEmiAmount);
			}
		}		
	}
	
	public void addPayments(Payment payment) {
		payments.put(payment.getEmiNumber(), payment);
	}	
}
