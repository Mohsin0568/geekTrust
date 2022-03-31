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
	 * 
	 * Main method which takes file location as an argument.
	 * Read file and execute all commands given in the file
	 * Creates a ledger and maintain all loan records in the ledger
	 * 
	 * @param args file location
	 * 
	 * return void
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
	
	/**
	 * 
	 * Creates a new ledger during the startup of the application 
	 * and maintains only one ledger object throughout the application life cycle.
	 * 
	 */
	private void initiateLedger() {
		ledger = Ledger.getInstance();
	}
	
	
	/**
	 * 
	 * Executes command given in the argument.
	 * Following are the commands which will be executed by this method
	 * 1. LOAN -- creates a new loan entry in the ledger.
	 * 2. PAYMENT -- creates a payment entry against the loan in the ledger.
	 * 3. BALANCE -- provides loan and emi balance to the customer
	 * 
	 * @param String command
	 */
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
	
	/**
	 * 
	 * Creates a new loan entry in the ledger for the customer.
	 *  
	 * @param String[], it has following elements
	 * 1. Command
	 * 2. Bank Name
	 * 3. Customer Name
	 * 4. Principal Amount
	 * 5. Loan Tenure in years
	 * 6. Rate of interest  
	 */
	private void processLoan(String ... data) {
		Loan loan = new Loan(data[2], // customer name 
				data[1], // bank name
				Long.parseLong(data[3]), // principal amount
				Integer.parseInt(data[4]), // tenure years
				Float.parseFloat(data[5])); // rate of interest
		
		ledger.addNewLoan(loan);		
	}
	
	/**
	 * 
	 * Displays loan pending amount and pending emi's to the customer. 
	 *  
	 * @param String[], it has following elements
	 * 1. Command
	 * 2. Bank Name
	 * 3. Customer Name
	 * 4. Lumsum Amount
	 * 5. After emi
	 * 
	 */
	private void viewBalance(String ... data) {
		Optional<Loan> optionalLoan = ledger.getLoan(data[2], data[1]);
		if(optionalLoan.isPresent()) { // going to print balance if loan is available in ledger
			Loan customerLoan = optionalLoan.get();			
			String enquiryDetails = customerLoan.enquiry(Integer.parseInt(data[3])); // get total amount paid and number of emi's left
			System.out.print(customerLoan.getBankName() + " " + customerLoan.getCustomerName()+" ");
			System.out.println(enquiryDetails);
		}
	}
	
	/**
	 * 
	 * Add payment information for a customer against a loan in the ledger. 
	 *  
	 * @param String[], it has following elements
	 * 1. Command
	 * 2. Bank Name
	 * 3. Customer Name
	 * 4. Balance to fetch after emi number
	 *  
	 */
	private void processPayment(String ... data) {
		Optional<Loan> optionalLoan = ledger.getLoan(data[2], data[1]);
		if(optionalLoan.isPresent()) { // add payment to the loan if loan is added in ledger
			Loan customerLoan = optionalLoan.get();
			
			// create payment object
			Payment payment = Payment.builder().lumsumAmount(Long.parseLong(data[3]))
						.emiNumber(Integer.parseInt(data[4])).build();
			
			// add payment to loan
			customerLoan.addPayments(payment);
		}
	}

}
