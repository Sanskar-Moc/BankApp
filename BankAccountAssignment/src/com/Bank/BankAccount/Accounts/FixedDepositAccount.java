package com.Bank.BankAccount.Accounts;

public class FixedDepositAccount extends Account {
	public double depositAmount;
	public int tenure;
	public double interest;
	private static double rate=8.0;
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public static double getRate() {
		return rate;
	}
	public FixedDepositAccount(long acno, double balance,String openingDate,
			double depositAmount,int tenure){
		super(acno,balance,openingDate);
		this.depositAmount=depositAmount;
		this.tenure=tenure;
	}
	public double getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(double depositAmount) {
		this.depositAmount = depositAmount;
	}
	public int getTenure() {
		return tenure;
	}
	public void setTenure(int tenure) {
		this.tenure = tenure;
	}
	


	@Override
	public String toString() {
		return "FixedDepositAccount [\n depositAmount=" + depositAmount + "\n tenure=" + tenure + "\n interest=" + interest
				+ "\n balance=" + balance + "\n openingDate=" + openingDate + "\n AccountNumber=" + accountNumber
				+ "\n IFSCCode=" + getIfscCode() + "\n BankName=" + getBankName() + "]";
	}
	@Override
	public double calculateInterest() {
		this.interest=this.depositAmount*FixedDepositAccount.rate*this.tenure/100.0;
		this.balance=this.depositAmount+this.interest;
		return this.interest;
	}
	
}
