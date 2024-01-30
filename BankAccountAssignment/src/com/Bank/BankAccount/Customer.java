package com.Bank.BankAccount;

import java.io.Serializable;

import com.Bank.BankAccount.Accounts.Account;

enum Sex{
	MALE,
	FEMALE
}

public class Customer implements Serializable {
	private static int refId=100;
	public int custId;
	
	public String custName;
	public int custAge;
	public long custMobile;
	public String custPassport;
	public Account bankAccount;
	public boolean accountAdded=false;
	public String DOB;
	public String emailId;
	public Sex sex;
	
	
	public Customer(String sex,String email,int custId,String custName,int custAge,long custMobile, String custPassport,String DOB) {
		
		if(sex.equals("Male")) {
			this.sex=Sex.MALE;			
		}
		else {
			this.sex=Sex.FEMALE;
		}
//		this.sex=sex;
		this.emailId=email;
		this.custId=custId;
		this.custName=custName;
		this.custAge=custAge;
		this.custMobile=custMobile;
		this.custPassport=custPassport;
		this.DOB=DOB;
	}
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public boolean isAccountAdded() {
		return accountAdded;
	}
	public void setAccountAdded(boolean accountAdded) {
		this.accountAdded = accountAdded;
	}

	public Account getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(Account bankAccount) {
		this.bankAccount = bankAccount;
	}

	

	public static int getrefId() {
		return refId;
	}
	public static void setRefId(int id) {
		Customer.refId=id;
	}
	public static void incrementrefId() {
		Customer.refId++;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public int getCustAge() {
		return custAge;
	}
	public void setCustAge(int custAge) {
		this.custAge = custAge;
	}
	public long getCustMobile() {
		return custMobile;
	}
	public void setCustMobile(long custMobile) {
		this.custMobile = custMobile;
	}
	public String getCustPassport() {
		return custPassport;
	}
	public void setCustPassport(String custPassport) {
		this.custPassport = custPassport;
	}
	@Override
	public String toString() {
		
		return "[Name: "+ this.custName
				+ "\n Customer ID: "+this.custId
				+ "\n Age: "+this.custAge
				+"\n Mobile: "+this.custMobile
				+"\n PassportID: "+this.custPassport
				+"\n DOB: "+this.DOB
				+"\n Bank Account Added: "+this.accountAdded
				+(this.accountAdded?"\n Bank Account Details: "+this.bankAccount:"")
				+"]";
	}
	
}
