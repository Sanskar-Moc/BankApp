package com.Bank.BankAccount;

import java.util.Comparator;

public class CustomerNameComparator implements Comparator<Customer> {

	@Override
	public int compare(Customer o1, Customer o2) {
		return o1.getCustName().compareTo(o2.getCustName());
	}

}
