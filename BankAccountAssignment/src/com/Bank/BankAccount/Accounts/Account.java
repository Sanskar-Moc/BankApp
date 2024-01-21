package com.Bank.BankAccount.Accounts;

import java.io.Serializable;

public abstract class Account implements Serializable {
	
	private static long refAccountNumber=1234510100;
	public long accountNumber;
	private static String ifscCode="SSBMW101000";
	private static String bankName="Solar System Bank of Milky Way";
	public double balance=0.0;
	public String openingDate;
	
	public Account(long acno,double balance,String openingDate) {
		this.accountNumber=acno;
		this.balance = balance;
		this.openingDate=openingDate;
	}
	public static long getRefAccountNumber() {
		return refAccountNumber;
	}
	public static void setRefAccountNumber(long acno) {
		Account.refAccountNumber=acno;
	}
	public static void acNoIncrement() {
		Account.refAccountNumber++;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getOpeningDate() {
		return openingDate;
	}
	public void setOpeningDate(String openingDate) {
		this.openingDate = openingDate;
	}
	@Override
	public String toString() {
		return "Account [\naccountNumber=" + accountNumber + "\n IFSC Code=" + ifscCode + "\n bankName=" + bankName
				+ "\n balance=" + balance + "\n openingDate=" + openingDate + "]";
	}
	public abstract double calculateInterest();
	

}
