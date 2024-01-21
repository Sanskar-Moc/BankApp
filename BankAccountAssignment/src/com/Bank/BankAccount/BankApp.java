package com.Bank.BankAccount;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import com.Bank.BankAccount.Accounts.Account;
import com.Bank.BankAccount.Accounts.CustomerNotFoundException;
import com.Bank.BankAccount.Accounts.FixedDepositAccount;
import com.Bank.BankAccount.Accounts.SavingsAccount;

public class BankApp {
	HashMap<Integer,Customer> customerData;
	HashSet<String> passportData;
	HashMap<String,List<Long>>nameToId;
	List<Customer>customers;
	Scanner sc;
	
//	int id;
	
	public BankApp(){// default constructor for inits
		customerData=new HashMap<>();
		passportData=new HashSet<>();
		nameToId=new HashMap<>();
		customers=new ArrayList<>();
		sc=new Scanner(System.in);
		//deserialization of data retreival of last persisted data
		deserializeData();
		long temp=nameToId.get("#").get(0);
		int k=(int)temp;
		Customer.setRefId(k);
		Account.setRefAccountNumber(nameToId.get("*").get(0));
	}
	public void start() {// start and infinite menu display of application
		System.out.println(
				"0. Deposit/Withdraw in Savings Account through ID\r\n"
				+ "1. Create New Customer Data\r\n"
				+ "2. Assign a Bank Account to a Customer\r\n"
				+ "3. Display balance or interest earned of a Customer\r\n"
				+ "4. Sort Customer Data\r\n"
				+ "5. Persist Customer Data\r\n"
				+ "6. Show All Customers\r\n"
				+ "7. Search Customers by Name\r\n"
				+ "8. Exit \r\n"
				+ "\\/");

		int input=sc.nextInt();
		while(true) {
			choice(input);
		}
	}
	public void assign(int id) throws InsufficientBalanceException { // assigns bank account to a Customer instance
		
		String currDate=LocalDate.now().toString();
		System.out.println("Enter SA for Savings Account / FD for Fixed Deposit Account: ");
		sc.nextLine();
		String choice=validType();
		Account newAccount;
	
		// choose savings or fixed deposit
		if(choice.equals("SA")) {
			System.out.println("Enter Opening Balance: ");
			double bal=enterBalance();
			System.out.println("Enter 0 if it is a Salary Account"); // check if salary account
			int isSal=sc.nextInt();
			boolean sal=isSal==0;
			//			System.out.println("Enter minimum balance: ");
			double minBal=sal?0:100;
			if(bal<minBal) throw new InsufficientBalanceException("“Insufficient balance for Savings Account, Minimum balance should be\r\n"
					+ "100");
			newAccount=new SavingsAccount(Account.getRefAccountNumber(),bal,currDate,sal,minBal);
		}
		else {
			System.out.println("Enter deposit amount: ");
			double deposit=enterBalance();
			System.out.println("Enter tenure: ");
			int ten=enterTenure();
			newAccount=new FixedDepositAccount(Account.getRefAccountNumber(),0,currDate,deposit,ten);
		}
	
		Customer temp=customerData.get(id);
		temp.setBankAccount(newAccount);
		temp.setAccountAdded(true);
		Account.acNoIncrement();
		
		// store account number reference
		List<Long>t=new ArrayList<>();
		t.add( Account.getRefAccountNumber());
		nameToId.put("*",t );
		
		System.out.println("Account Added Successfully.");
	
	}
	public void assignBankAccount(){ // abstracat of bak account assignment
			System.out.println("Enter Customer id: ");
			int id=sc.nextInt();
			try {
				if(checkCustomer(id)) {
					try {
						assign(id);
					} catch (InsufficientBalanceException e) {
						// TODO Auto-generated catch block
						System.out.println("Insufficient Balance");
	//					e.printStackTrace();
					}
				}	
			}
			catch(CustomerNotFoundException e) {
				System.out.println(e);
			}
			
			//		else {
			//			CustomerNotFoundException cnfe=new CustomerNotFoundException("Customer with the given id "+id+" not dound." );
			//			throw cnfe;
			////			System.out.println("Invalid id, enter valid customer id: ");
			////			assignBankAccount();
			//		}
			
		}
	public void choice(int choice) { // main switch choice method
			switch(choice) {
			case 0:
				withdrawOrDeposit();
				start();
				break;
			case 1:
				createNewCustomer();
				start();
				break;
			case 2:
				assignBankAccount();
				start();
				break;
			case 3:
				displayBalAndInterest();
				start();
				break;
			case 4:
				displaySortedCustomer();
				start();
				break;
			case 5:
				//				System.out.println("Option not available yet");
				persistData();
				start();
				break;
			case 6:
				showAllCustomers();
				start();
				break;
			case 7:
				try {
					searchCustomerByName();
				} catch (CustomerNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("Customer does not exist");
	//				e.printStackTrace();
				}
				start();
				break;
			case 8:
				exit();
				break;
			default:
				System.out.println("Choice does not exist, try again");
				start();
				break;
			}
		}
	public void createNewCustomer() { // creates new customer and inputs details
		System.out.println("==========================================");
		System.out.println("Enter Name:");
		sc.nextLine();
		String name=sc.nextLine();
		//		sc.next();
		//		System.out.println("Enter Age :");
		//		int age=sc.nextInt();
		System.out.println("Enter Mobile Number :");
		long mobile=enterMobile();
		System.out.println("Enter Passport Id :");
		sc.nextLine();
		String passport=enterPassport();
		System.out.println("Enter DOB in(YYYY-MM-DD) format :");
		//		sc.nextLine();
		int age[]=new int[1];
		String DOB=enterDOB(age);
		Customer newCustomer=new Customer(Customer.getrefId(),name,age[0],mobile,passport,DOB);
		customers.add(newCustomer);
		customerData.put(Customer.getrefId(),newCustomer);
		if(!nameToId.containsKey(name)) {
			List<Long>temp=new ArrayList<>();
			temp.add((long) Customer.getrefId());
			nameToId.put(name,temp );
		}
		else {
			nameToId.get(name).add((long)Customer.getrefId());
		}
	
	
		System.out.println("New Customer added successfully.");
		System.out.println("Customer ID: "+Customer.getrefId()+"\n"+newCustomer);
	
		Customer.incrementrefId();
	
		//storing reference id
		List<Long>temp=new ArrayList<>();
		temp.add((long) Customer.getrefId());
		nameToId.put("#",temp);
	}
	public boolean checkCustomer(int id) throws CustomerNotFoundException { // checks if customer id exists in the store
		if(customerData.containsKey(id))return true;
		else {
			CustomerNotFoundException cnfe=new CustomerNotFoundException("Customer with the given id "+id+" not found." );
			throw cnfe;
		}
	}
	@SuppressWarnings("unchecked") // deserializer
	public void deserializeData() {
		try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("C:\\C376\\data.dat")))) {
			// Read the serialized objects
			Object obj1 = ois.readObject();
			Object obj2 = ois.readObject();
			Object obj3 = ois.readObject();
			Object obj4 = ois.readObject();
	
			// Check if the deserialized objects are of the expected types
			if (obj1 instanceof HashMap && obj2 instanceof HashSet && obj3 instanceof HashMap && obj4 instanceof ArrayList) {
				// Cast the objects back to their respective types
				this.customerData = (HashMap<Integer, Customer>) obj1;
	
				this.passportData = (HashSet<String>) obj2;
	
				this.nameToId = (HashMap<String, List<Long>>) obj3;
	
				this.customers=(ArrayList<Customer>)obj4;
	
				// Display the deserialized objects
				//                System.out.println("Deserialized Customer Data: " + customerData);
				//                System.out.println("Deserialized Passport Data: " + passportData);
				//                System.out.println("Deserialized Name to ID Mapping: " + nameToId);
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void displayBalAndInterest() { //displays bal and interest of a customer id
		System.out.println("Enter Customer id: ");
		int id=sc.nextInt();
		try {
			if(checkCustomer(id)) {
				Customer temp=customerData.get(id);
				if(temp.accountAdded) {
					System.out.println("Balance: "+temp.getBankAccount().balance);
					System.out.println("Interest: "+temp.getBankAccount().calculateInterest());
				}
				else {
					System.out.println("No Account assigned for the provided CustomerID");
				}
			}	
		}
		catch(CustomerNotFoundException e) {
			System.out.println(e);
		}
	}
	public void displaySortedCustomer() { // sorts according to choices
		System.out.println("Enter-> \n1:Sort by Customer Name \n2:Sort by Customer ID \n3:Sort by Balance");
		int input=sc.nextInt();
		switch(input) {
		case 1:
	
			Collections.sort(customers, new CustomerNameComparator());
			System.out.println(customers);
			break;
		case 2:
			Collections.sort(customers,new CustomerIdComparator());
			System.out.println(customers);
			break;
		case 3:
			Collections.sort(customers,new CustomerBalanceComparator());
			System.out.println(customers);
			break;
		default:
			System.out.println("Invalid Choice, Try again");
			displaySortedCustomer();
			break;
		}
	}
	public double enterBalance() { // balance input
		double bal=sc.nextDouble();
		if(bal<0.0) {
			System.out.println("Cant have negative fund input, try again: ");
			bal=enterBalance();
		}
		return bal;
	}
	public String enterDOB(int a[]) { // enter date of birth
		String DOB=sc.nextLine();
		if(!isValidDate(DOB,a)) {
			System.out.println("Please enter valid date, try again :");
			DOB=enterDOB(a);
		}
	
		return DOB;
	}
	public long enterMobile() { // take input of mobile number
		long number=sc.nextLong();
		int length = (int) (Math. log10(number) + 1);
		if(length!=10) {
			System.out.println("Invalid Mobile Number Length, renter :");
			number=enterMobile();
		}
		return number;
	}
	public String enterPassport() { // enter passport id
		String passport=sc.nextLine();
		if(!passportData.add(passport)) {
			System.out.println("Passport id already exist, renter correct id :");
			passport=enterPassport();
		}
		return passport;
	}
	public int enterTenure() { // tenure input
		int ten=sc.nextInt();
		if(ten<1 || ten>7) {
			System.out.println("Can only have tenure for [1,7]yrs, try again: ");
			ten=enterTenure();
		}
		return ten;
	}
	public boolean isValidDate(String DOB,int a[]) { // check if entered date is valid or not
		String parts[]=DOB.split("-");
		int year=Integer.parseInt(parts[0]);
		int month=Integer.parseInt(parts[1]);
		int day=Integer.parseInt(parts[2]);
		int currYear= Year.now().getValue();
		a[0]=currYear-year;
		if(a[0]<10) {
			System.out.println("Can have account only for age>10, Invalid Input");
			return false;
		}
		if(month>0 && month<13) {
			if(month<=7) {
				if(month==2) {
					if(year%4==0) {
						if(day>29)return false;
						else return true;
					}
					else {
						if(day>28)return false;
						else return true;
					}
				}
				else if(month%2==1) {
					if(day>31)return false;
					else return true;
				}
				else {
					if(day>30)return false;
					return true;
				}
			}
			else {
				if(month%2==0) {
					if(day>31)return false;
					else return true;
				}
				else {
					if(day>30)return false;
					return true;
				}
			}
		}
		else {
			return false;
		}

	}
	// Serialize the objects with buffers
	public void persistData() {
		System.out.println("Enter choice: \n1:Store in FileSystem \n2:Store in DBMS");
		int n=sc.nextInt();
		switch(n) {
		case 1:
			FileStorageDao fsdao=new FileStorageDao();
			fsdao.serialize(customerData, passportData, nameToId, customers);
			break;
		case 2:
			System.out.println("No implementation yet");
			persistData();
			break;
		default:
			System.out.println("Invalid choide, Try again");
			persistData();
			break;
		}
	
	}
	public void searchCustomerByName() throws CustomerNotFoundException{ //search customers by name
		System.out.println("Enter name: ");
		sc.nextLine();
		String name=sc.nextLine();
		if(nameToId.containsKey(name)) {
			List<Long>ids=nameToId.get(name);
			for(long id:ids) {
				int temp=(int)id;
				System.out.println("=======================================================");
				System.out.println(customerData.get(temp));
			}
		}
		else {			
			throw new CustomerNotFoundException("No Customer with such name exist.");
		}
	}
	public void showAllCustomers() { // display all customers
		System.out.println("All Customers: \n");
		System.out.println(customerData);
		start();
	}
	public void transacOptions(SavingsAccount a) {//choose transaction options
			System.out.println("Enter option->"
					+"\n1. Withdraw"
					+"\n2. Deposit"
					+"\n3. Check Balance"
					);
			byte opts=sc.nextByte();
			double amt;
			switch(opts) {
				case 1:
					System.out.println("Enter Amount: ");
					amt=sc.nextDouble();
					try {
						a.withdraw(amt);
					} catch (InsufficientBalanceException e) {
						// TODO Auto-generated catch block
						System.out.println("Insufficient Funds");
	//					e.printStackTrace();
					}
					break;
				case 2:
					System.out.println("Enter Amount: ");
					amt=sc.nextDouble();
					a.deposit(amt);
					break;
				case 3:
					a.checkBalance();
					break;
				default:
					System.out.println("Invalid choice, try again");
					transacOptions(a);
					break;
			}
		}
	public String validType() { // checks if account is FD Account or SA account 
		String choice=sc.nextLine();
		if(choice.equals("SA")||choice.equals("FD")) {
			return choice;
		}
		else {
			System.out.println("Please enter FD / SA: ");
			return validType();
		}
	}
	public void withdrawOrDeposit() { //check metas about customer account
		System.out.println("Enter Customer ID");
		int id=sc.nextInt();
		try {
			if(checkCustomer(id)) {
				Customer temp=customerData.get(id);
				Account a=temp.getBankAccount();
				if(a instanceof SavingsAccount) {
					transacOptions((SavingsAccount) a);
				}
				else {
					System.out.println("Can't withdraw/deposit from FD Bank Account");
				}
			}
		} catch (CustomerNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Customer does not exist");
//			e.printStackTrace();
		}
	}
	
	public void exit() { // exit the app
		System.out.println("Exiting now...");
		sc.close();
		System.exit(0);
	}
}
