import java.util.Date;

public class Account {
	private int id;
	private double balance;
	private double annualInterestRate;
	private Date dateCreated;


	public static void main(String[] args) {
		// Create an Account object 
		Account account = new Account();
		account.setId(1122);
		account.setBalance(20000);
		account.setAnnualInterestRate(4.5);
		
		// Use withdraw method 
		account.withdraw(2500);
		
		// Use deposit method 
		account.deposit(3000);
		
		System.out.println("Welcome Account # " + account.getId());
		System.out.println("Account Balance = $" + account.getBalance());
		System.out.println("Monthly Interest Rate of " + account.getMonthlyInterestRate() + "%");
		System.out.println("Account was created on " + account.getDateCreated());
		
	}
	
	
//******************************************************************************//
			
	// Constructors //
	
	// Default no-arg constructor 
	public Account() { 
		this.id = 0;
		this.balance = 0;
		this.dateCreated = new Date();
	}
	
	// Constructor to create account with id and balance 
	public Account(int newId, double newBalance) {
		id = newId;
		balance = newBalance;
		this.dateCreated = new Date();
	}
	
//******************************************************************************//
		
	// Accessor and Mutator Methods //
	
	// getter method for id
	public int getId() {
		return id;
	}
	
	// setter method for id 
	public void setId(int newId) {
		this.id = newId;
	}
	
	// getter for balance 
	public double getBalance() {
		return balance;
	}
	
	// setter for balance 
	public void setBalance(double newBalance) {
		this.balance = newBalance;
	}
	
	// getter for annualInterestRate
	public double getAnnualInterestRate() {
		return annualInterestRate;
	}
	
	// setter for annualInterestRate
	public void setAnnualInterestRate(double annualInterestRate) {
		this.annualInterestRate = annualInterestRate;
	}
	
	public Date getDateCreated() {
		return this.dateCreated;
	}
	
//******************************************************************************//
	
	// Other Methods //
	
	// getMonthlyInterestRate method 
	public double getMonthlyInterestRate() {
		double monthlyInterestRate = (annualInterestRate / 1200) * 100;
		return monthlyInterestRate;
	}
	
	// withdrawal method 
	public double withdraw(double withdrawAmt) {
		if (this.balance != 0) {
		this.balance = balance - withdrawAmt;
		}
		if (this.balance == 0) {
			System.out.println("No available funds for withdraw");
		}
		return balance;
	}
	
	// deposit method 
	public double deposit(double depositAmt) {
		this.balance = balance + depositAmt;
		return balance;
	}
	

} // Account class bracket 
