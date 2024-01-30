package com.Bank.BankAccount;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import com.Bank.BankAccount.Accounts.Account;
import com.Bank.BankAccount.Accounts.CustomerNotFoundException;
import com.Bank.BankAccount.Accounts.FixedDepositAccount;
import com.Bank.BankAccount.Accounts.SavingsAccount;

public class BankApp {
	public HashMap<Integer,Customer> customerData;
	public HashSet<String> passportData;
	public HashMap<String,List<Long>>nameToId;
	public List<Customer>customers;
	Scanner sc;
	
//	int id;
	
	public BankApp(){// default constructor for inits
		customerData=new HashMap<>();
		passportData=new HashSet<>();
		nameToId=new HashMap<>();
		customers=new ArrayList<>();
		sc=new Scanner(System.in);
		//deserialization of data retreival of last persisted data
		FileStorageDao.deserializeData(this);
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
	public void assign(int id,String choice) throws InsufficientBalanceException { // assigns bank account to a Customer instance
		
		String currDate=LocalDate.now().toString();
//		System.out.println("Enter SA for Savings Account / FD for Fixed Deposit Account: ");
//		sc.nextLine();
//		String choice=validType();
		Account newAccount;
	
		// choose savings or fixed deposit
		if(choice.equals("SA")) {
//			System.out.println("Enter Opening Balance: ");
			
			double bal=enterBalance();
//			double bal=Double.parseDouble(userInput);
//			System.out.println("Enter 0 if it is a Salary Account"); // check if salary account
			String isSal = JOptionPane.showInputDialog(null, "Is it a Salary Account Yes/No");
//			int isSal=sc.nextInt();
//			boolean sal=isSal==0;
			boolean sal=isSal.equals("Yes");
			//			System.out.println("Enter minimum balance: ");
			double minBal=sal?0:100;
			if(bal<minBal) throw new InsufficientBalanceException("“Insufficient balance for Savings Account, Minimum balance should be\r\n"
					+ "100");
			newAccount=new SavingsAccount(Account.getRefAccountNumber(),bal,currDate,sal,minBal);
		}
		else {
//			System.out.println("Enter deposit amount: ");
			double deposit=enterBalance();
//			System.out.println("Enter tenure: ");
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
		serialize();
		System.out.println("Account Added Successfully.");
	
	}
	public void assignBankAccount(){ // abstracat of bak account assignment
			System.out.println("Enter Customer id: ");
			int id=sc.nextInt();
			try {
				if(checkCustomer(id)) {
					try {
						assign(id,"");
					} catch (InsufficientBalanceException e) {
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
				withdrawOrDeposit(0);
				start();
				break;
			case 1:
//				createNewCustomer();
				start();
				break;
			case 2:
				assignBankAccount();
				start();
				break;
			case 3:
//				displayBalAndInterest();
				start();
				break;
			case 4:
//				displaySortedCustomer();
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
					searchCustomerByName("");
				} catch (CustomerNotFoundException e) {
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
	public void createNewCustomer(String sex,String name,long mobile,String passport,LocalDate DOB,String email) { // creates new customer and inputs details
//		System.out.println("==========================================");
//		System.out.println("Enter Name:");
//		sc.nextLine();
//		String name=sc.nextLine();
		//		sc.next();
		//		System.out.println("Enter Age :");
		//		int age=sc.nextInt();
//		System.out.println("Enter Mobile Number :");
//		long mobile=enterMobile();
//		System.out.println("Enter Passport Id :");
//		sc.nextLine();
//		String passport=enterPassport();
//		System.out.println("Enter DOB in(YYYY-MM-DD) format :");
		//		sc.nextLine();
		int age[]=new int[1];
		isValidDate(DOB,age);
		Customer newCustomer=new Customer(sex,email,Customer.getrefId(),name,age[0],mobile,passport,DOB.toString());
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
		
		Thread t1=new Thread(()->{
			EmailSender.send(email, "Hello "+name+" your account is successfully created.");			
		});
		t1.start();
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
	
	public void displayBalAndInterest(int id) { //displays bal and interest of a customer id
//		System.out.println("Enter Customer id: ");
//		int id=sc.nextInt();
//		try {
//			if(checkCustomer(id)) {
				Customer temp=customerData.get(id);
				if(temp.accountAdded) {
					String message="Balance: "+temp.getBankAccount().balance
							+"\n"+"Interest: "+temp.getBankAccount().calculateInterest();
//					System.out.println("Balance: "+temp.getBankAccount().balance);
//					System.out.println("Interest: "+temp.getBankAccount().calculateInterest());
					JOptionPane.showMessageDialog(
			                null,
			                message,
			                "Success",
			                JOptionPane.INFORMATION_MESSAGE
			        );
					serialize();
				}
				else {
					JOptionPane.showMessageDialog(null, "No Account assigned for the provided Customer ID", "Error", JOptionPane.ERROR_MESSAGE);
//					System.out.println("No Account assigned for the provided CustomerID");
				}
//			}	
//		}
//		catch(CustomerNotFoundException e) {
//			System.out.println(e);
//		}
	}
	public List<Customer> displaySortedCustomer(int input) { // sorts according to choices
//		System.out.println("Enter-> \n1:Sort by Customer Name \n2:Sort by Customer ID \n3:Sort by Balance");
//		int input=sc.nextInt();
		switch(input) {
		case 1:
	
			Collections.sort(customers, new CustomerNameComparator());
//			System.out.println(customers);
			break;
		case 2:
			Collections.sort(customers,new CustomerIdComparator());
//			System.out.println(customers);
			break;
		case 3:
			Collections.sort(customers,new CustomerBalanceComparator());
//			System.out.println(customers);
			break;
		default:
			System.out.println("Invalid Choice, Try again");
//			displaySortedCustomer();
			break;
		}
		return customers;
	}
	public double enterBalance() { // balance input
		String userInput = JOptionPane.showInputDialog(null, "Enter Balance:");
		double bal=Double.parseDouble(userInput);
		
		if(bal<0.0) {
//			System.out.println("Cant have negative fund input, try again: ");
			JOptionPane.showMessageDialog(null, "Cant have negative fund input, try again", "Error", JOptionPane.ERROR_MESSAGE);
			bal=enterBalance();
		}
		return bal;
	}
	public String enterDOB(int a[]) { // enter date of birth
//		String DOB=sc.nextLine();
		LocalDate DOB=LocalDate.now();
		if(!isValidDate(DOB,a)) {
			System.out.println("Please enter valid date, try again :");
//			DOB=enterDOB(a);
		}
	
		return DOB.toString();
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
	//		int ten=sc.nextInt();
			String userInput = JOptionPane.showInputDialog(null, "Enter Tenure [1,7]yrs:");
			int ten=Integer.parseInt(userInput);
			if(ten<1 || ten>7) {
	//			System.out.println("Can only have tenure for [1,7]yrs, try again: ");
				JOptionPane.showMessageDialog(null, "Can only have tenure for [1,7]yrs, try again", "Error", JOptionPane.ERROR_MESSAGE);
				ten=enterTenure();
			}
			return ten;
		}
	public boolean checkPassport(String p) {
		return passportData.add(p);
	}
	public boolean isValidDate(LocalDate DOB,int a[]) { // check if entered date is valid or not
//		String parts[]=DOB.split("-");
		int year=DOB.getYear();
		int month=DOB.getMonthValue();
		int day=DOB.getDayOfMonth();
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
	public void serialize() {
		FileStorageDao fsdao=new FileStorageDao();
		fsdao.serialize(customerData, passportData, nameToId, customers);
	}
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
	public List<Customer> searchCustomerByName(String name) throws CustomerNotFoundException{ //search customers by name
//		System.out.println("Enter name: ");
//		sc.nextLine();
//		String name=sc.nextLine();
		List<Customer>byNames;
		if(nameToId.containsKey(name)) {
			List<Long>ids=nameToId.get(name);
			
			byNames=ids.stream()
					.mapToInt((n)->{
						String s=n.toString();
						int t=Integer.parseInt(s);
						return t;
					})
					.mapToObj(customerData::get)
					.collect(Collectors.toList());
//			System.out.println(byNames);
			return byNames;
			
//			for(long id:ids) {
//				int temp=(int)id;
//				System.out.println("=======================================================");
//				System.out.println(customerData.get(temp));
//			}
		}
		else {			
			throw new CustomerNotFoundException("No Customer with such name exist.");
		}
//		return byNames;
	}
	public void showAllCustomers() { // display all customers
		System.out.println("All Customers: \n");
		System.out.println(customerData);
		start();
	}
	public void transacOptions(SavingsAccount a,int id) {//choose transaction options
//			System.out.println("Enter option->"
//					+"\n1. Withdraw"
//					+"\n2. Deposit"
//					+"\n3. Check Balance"
//					);
			String[] choices = {"Withdraw", "Deposit","Check Balance"};
	
	        // Show an input dialog with a choice box
	        String selectedChoiceObject = (String)JOptionPane.showInputDialog(
	                null,
	                "Choose an option:",
	                "Choice Input Dialog",
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                choices,
	                choices[0] // Default selection
	        );
//			byte opts=sc.nextByte();
	        byte opts=0;
	        for(String s:choices) {
	        	if(s.equals(selectedChoiceObject))
	        		break;
	        	opts++;
	        }
	        System.out.println(opts);
			double amt;
			Customer c=customerData.get(id);
			switch(opts) {
				case 0:
//					System.out.println("Enter Amount: ");
//					amt=sc.nextDouble();
					String userInput = JOptionPane.showInputDialog(null, "Enter Amount to withdraw: ");
					amt=Double.parseDouble(userInput);
					try {
						a.withdraw(amt);
						JOptionPane.showMessageDialog(null, "Withdrawn Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
						Thread t1=new Thread(()->{
							String message="Hello "+c.getCustName()+" an amount of Rs."+amt+" is withdrawn from your Account"
									+" with CustomerID:"+id;
							EmailSender.send(c.emailId,message );
						});
						t1.start();
					} catch (InsufficientBalanceException e) {
//						System.out.println("Insufficient Funds");
	//					e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Insufficient Funds", "Error", JOptionPane.ERROR_MESSAGE);
					}
					break;
				case 1:
//					System.out.println("Enter Amount: ");
//					amt=sc.nextDouble();
					userInput = JOptionPane.showInputDialog(null, "Enter Amount to withdraw: ");
					amt=Double.parseDouble(userInput);
					a.deposit(amt);
					JOptionPane.showMessageDialog(null, "Deposited Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
					Thread t1=new Thread(()->{
						String message="Hello "+c.getCustName()+" an amount of Rs."+amt+" is deposited to your Account"
								+" with CustomerID:"+id;
						EmailSender.send(c.emailId,message);
					});
					t1.start();
					break;
				case 2:
//					a.checkBalance();
					
					JOptionPane.showMessageDialog(null, "Your Balance is "+a.balance, "Info", JOptionPane.INFORMATION_MESSAGE);
					break;
				default:
					System.out.println("Invalid choice, try again");
//					transacOptions(a);
					break;
			}
			serialize();
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
	public void withdrawOrDeposit(int id) { //check metas about customer account
//		System.out.println("Enter Customer ID");
//		int id=sc.nextInt();
		try {
			if(checkCustomer(id)) {
				Customer temp=customerData.get(id);
				Account a=temp.getBankAccount();
				if(a instanceof SavingsAccount) {
					transacOptions((SavingsAccount) a,id);
				}
				else {
//					System.out.println("Can't withdraw/deposit from FD Bank Account");
					JOptionPane.showMessageDialog(null, "Can't withdraw/deposit from FD Bank Account", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		} catch (CustomerNotFoundException e) {
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
