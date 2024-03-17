package com.Bank;

import java.sql.SQLException;

//import com.Bank.BankAccount.BankApp;
import com.Bank.BankAccount.EmailSender;
import com.Bank.database.JDBCUtils;
import com.Bank.frontend.MainMenu;

public class TestBank {

	public static void main(String[] args) {
//		BankApp ba=new BankApp();
//		ba.start();
//		EmailSender es=new EmailSender();
//		es.send();
		new MainMenu();
	}

}
