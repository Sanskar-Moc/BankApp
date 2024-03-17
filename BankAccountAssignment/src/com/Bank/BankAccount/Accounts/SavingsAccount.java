package com.Bank.BankAccount.Accounts;

import com.Bank.exceptions.InsufficientBalanceException;

public class SavingsAccount extends Account {
	public boolean isSalaryAccount;
	public double minBal;
	public double interest;
	private static double rate=4.0;
	
	public SavingsAccount(long acno,double balance,String openingDate,
			boolean isSalaryAccount,double minBal){
		super(acno,balance,openingDate);
		this.isSalaryAccount=isSalaryAccount;
		this.minBal=minBal;
	}
	
	public void deposit(double funds) {
		this.balance+=funds;
	}
	public void withdraw(double funds) throws InsufficientBalanceException {
		if(this.balance-funds<0) throw new InsufficientBalanceException("Not enough funds, your current balance is: "+this.balance);
		this.balance-=funds;
	}
	public void checkBalance() {
		System.out.println("Your current balance is: "+this.balance);
	}
	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public static double getRate() {
		return rate;
	}

	public boolean isSalaryAccount() {
		return isSalaryAccount;
	}
	public void setSalaryAccount(boolean isSalaryAccount) {
		this.isSalaryAccount = isSalaryAccount;
	}
	public double getMinBal() {
		return minBal;
	}
	public void setMinBal(double minBal) {
		this.minBal = minBal;
	}
	



	@Override
	public String toString() {
		return "SavingsAccount [\n isSalaryAccount=" + isSalaryAccount + "\n minBal=" + minBal + "\n interest=" + interest
				+ "\n balance=" + balance + "\n openingDate=" + openingDate + "\n AccountNumber=" + accountNumber
				+ "\n IFSCCode=" + getIfscCode() + "\n BankName=" + getBankName() + "]";
	}

	@Override
	public double calculateInterest() {
		this.interest=this.balance*SavingsAccount.rate/100.0;
		return this.interest;
	}
}
