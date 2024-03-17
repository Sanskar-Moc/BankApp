package com.Bank.BankAccount.customers;

import java.util.Comparator;

public class CustomerBalanceComparator implements Comparator<Customer> {

	@Override
	public int compare(Customer o1, Customer o2) {
		if(o1.isAccountAdded() && o2.isAccountAdded()) {
			return Double.compare(o1.bankAccount.getBalance(),o2.bankAccount.getBalance());
		}
		return 0;
	}

}
