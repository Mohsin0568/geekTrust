/**
 * 
 */
package com.ledger.main;

import static com.ledger.main.GeekConstants.BALANCE_STR;
import static com.ledger.main.GeekConstants.LOAN_STR;
import static com.ledger.main.GeekConstants.PAYMENT_STR;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author mohsin
 *
 */
public class Geektrust {
	
	private Ledger ledger;

	/**
	 * @param args
	 * 
	 * 
	 */
	public static void main(String[] args) {
		
		Geektrust geekTrust = new Geektrust();
		geekTrust.initiateLedger(); // creating ledger for application
		
		try(Stream<String> filesStream = Files.lines(Paths.get(args[0]))){
			filesStream.forEach(geekTrust :: executeCommand);
		}
		catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private void initiateLedger() {
		ledger = Ledger.getInstance();
	}
	
	private void executeCommand(String command) {
		if(command == null || command.isEmpty() || !command.contains(" "))
			return;
		
		String[] data = command.split(" ");		
		
		switch(data[0]) {
			
			case LOAN_STR:
				processLoan(data);
				break;			
			
			case PAYMENT_STR:
				processPayment(data);
				break;			
			
			case BALANCE_STR:
				viewBalance(data);
				break;			
			
			default:
				break;
		}		
	}
	
	private void processLoan(String ... data) {
		Loan loan = new Loan(data[2], // customer name 
				data[1], // bank name
				Long.parseLong(data[3]), // principal amount
				Integer.parseInt(data[4]), // tenure years
				Float.parseFloat(data[5])); // rate of interest
		
		ledger.addNewLoan(loan);		
	}
	
	private void viewBalance(String ... data) {
		Optional<Loan> optionalLoan = ledger.getLoan(data[2], data[1]);
		if(optionalLoan.isPresent()) {
			Loan customerLoan = optionalLoan.get();			
			String enquiryDetails = customerLoan.enquiry(Integer.parseInt(data[3]));
			System.out.print(customerLoan.getBankName() + " " + customerLoan.getCustomerName()+" ");
			System.out.println(enquiryDetails);
		}
	}
	
	private void processPayment(String ... data) {
		Optional<Loan> optionalLoan = ledger.getLoan(data[2], data[1]);
		if(optionalLoan.isPresent()) {
			Loan customerLoan = optionalLoan.get();
			
			Payment payment = Payment.builder().lumsumAmount(Long.parseLong(data[3]))
						.emiNumber(Integer.parseInt(data[4])).build();
			
			customerLoan.addPayments(payment);
		}
	}

}
