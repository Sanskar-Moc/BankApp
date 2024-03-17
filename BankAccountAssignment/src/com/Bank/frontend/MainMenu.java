package com.Bank.frontend;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.Bank.BankAccount.BankApp;
import com.Bank.database.JDBCUtils;
import com.Bank.exceptions.CustomerNotFoundException;
import com.Bank.exceptions.InsufficientBalanceException;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Button;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;

public class MainMenu extends JFrame {
	BankApp ba;
	public MainMenu() {
		
		setUndecorated(true);
		getContentPane().setBackground(Color.WHITE);
		setPreferredSize(new Dimension(600, 500));
		setSize(new Dimension(600, 500));
		setMinimumSize(new Dimension(600, 500));
		setLocationByPlatform(true);
		setVisible(true);
//		ba=this.ba;
		 ba=new BankApp();
		setMaximumSize(new Dimension(600, 500));
		setName("MainMenu");
		setTitle("Main Menu");
		setBackground(Color.DARK_GRAY);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(8, 77, 584, 384);
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton createNewCustomerButton_1 = new JButton("Create New Customer");
		createNewCustomerButton_1.setBackground(Color.WHITE);
		panel.add(createNewCustomerButton_1);
		createNewCustomerButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new CustomerForm(ba);
			}
		});
		createNewCustomerButton_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JButton assignAccountButton = new JButton("Assign Bank Account");
		assignAccountButton.setBackground(Color.WHITE);
		panel.add(assignAccountButton);
		assignAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userInput = JOptionPane.showInputDialog(null, "Enter Customer ID:");
				int id=Integer.parseInt(userInput);
				try {
					if(JDBCUtils.checkCustomer(id)) {
						String[] choices = {"SA", "FD"};

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
				        try {
							ba.assign(id, selectedChoiceObject);
						} catch (InsufficientBalanceException e1) {
							// TODO Auto-generated catch block
//							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "Account Balance cant be less than 100", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "Customer ID does not Exist", "Error", JOptionPane.ERROR_MESSAGE);						
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Customer ID does not Exist", "Error", JOptionPane.ERROR_MESSAGE);
//					e1.printStackTrace();
				}
			}
		});
		assignAccountButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		assignAccountButton.setAutoscrolls(true);
		assignAccountButton.setActionCommand("");
		
		JButton showAllDataButton = new JButton("Show All Customers");
		showAllDataButton.setBackground(Color.WHITE);
		panel.add(showAllDataButton);
		showAllDataButton.setActionCommand("");
		showAllDataButton.setAutoscrolls(true);
		showAllDataButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		showAllDataButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
//				new ShowAllCustomersForm();
				javax.swing.SwingUtilities.invokeLater(() -> {
		            new ShowAllCustomersForm(ba.customerData,ba).setVisible(true);
		        });
			}
		});
		
		JButton displayBalButton = new JButton("Display Balance and Interest");
		displayBalButton.setBackground(Color.WHITE);
		panel.add(displayBalButton);
		displayBalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userInput = JOptionPane.showInputDialog(null, "Enter Customer ID:");
				int id=Integer.parseInt(userInput);
				try {
					if(JDBCUtils.checkCustomer(id)) {
						ba.displayBalAndInterest(id);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		displayBalButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		displayBalButton.setAutoscrolls(true);
		displayBalButton.setActionCommand("");
		
		JButton depositWithdrawButton = new JButton("Withdraw/Deposit");
		depositWithdrawButton.setBackground(Color.WHITE);
		panel.add(depositWithdrawButton);
		depositWithdrawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userInput = JOptionPane.showInputDialog(null, "Enter Customer ID:");
				int id=Integer.parseInt(userInput);
				try {
					if(JDBCUtils.checkCustomer(id)) {
						ba.withdrawOrDeposit(id);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Customer ID does not Exist", "Error", JOptionPane.ERROR_MESSAGE);
//					e1.printStackTrace();
				}
			}
		});
		depositWithdrawButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(201, 10, 187, 57);
		getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(1,2));
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(31, 78, 40, 13);
		panel_1.add(lblNewLabel);
		
		JButton closeButton = new JButton("X");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(
		                closeButton,
		                "Do you really want to close this Application?",
		                "Confirmation",
		                JOptionPane.YES_NO_OPTION);

		        if (option == JOptionPane.YES_OPTION) {
		            // User chose to close the window
		        	dispose();  // Close the window
		        }
			}
		});
		panel_1.add(closeButton);
		closeButton.setFont(new Font("Tahoma", Font.BOLD, 40));
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(522, 78, 40, 13);
		panel_1.add(lblNewLabel_1);
	}
}
