package com.Bank.frontend;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import com.Bank.BankAccount.BankApp;
import com.Bank.BankAccount.Customer;
import com.Bank.BankAccount.Accounts.Account;
import com.Bank.BankAccount.Accounts.CustomerNotFoundException;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ShowAllCustomersForm extends JFrame {

	
    private HashMap<Integer, Customer> customerData;
    BankApp ba;
    public ShowAllCustomersForm(HashMap<Integer, Customer> customerData,BankApp ba) {
    	setPreferredSize(new Dimension(1000, 500));
    	this.ba=ba;
    	setSize(new Dimension(1000, 500));
    	setResizable(false);
    	getContentPane().setPreferredSize(new Dimension(1000, 500));
    	getContentPane().setSize(new Dimension(1000, 500));
        this.customerData = customerData;

        setTitle("Customer Data Table");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the table model
        CustomerTableModel tableModel = new CustomerTableModel(customerData);
        getContentPane().setLayout(null);

        // Create the JTable
        
        
        
        
        
        
        
        JTable table = new JTable(tableModel);

        // Create a scroll pane and add the table to it
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 450));
        scrollPane.setSize(new Dimension(800, 450));
        scrollPane.setBounds(0, 0, 1000, 306);
        scrollPane.setFont(new Font("Tahoma", Font.PLAIN, 15));

        // Add the scroll pane to the frame
        getContentPane().add(scrollPane);
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		new MainMenu();
        	}
        });
        backButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        backButton.setBounds(10, 316, 140, 35);
        getContentPane().add(backButton);
        
        JButton sortByNameButton = new JButton("Sort by Name");
        sortByNameButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		sorts(1);
        	}
        });
        sortByNameButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        sortByNameButton.setBounds(359, 316, 119, 35);
        getContentPane().add(sortByNameButton);
        
        JButton sortByIdButton = new JButton("Sort by Id");
        sortByIdButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		sorts(2);
        	}
        });
        sortByIdButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        sortByIdButton.setBounds(486, 316, 119, 35);
        getContentPane().add(sortByIdButton);
        
        JButton sortByBalButton = new JButton("Sort by Balance");
        sortByBalButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		sorts(3);
        	}
        });
        sortByBalButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        sortByBalButton.setBounds(613, 316, 163, 35);
        getContentPane().add(sortByBalButton);
        
        JButton searchByNameButton = new JButton("Search by Name");
        searchByNameButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String userInput = JOptionPane.showInputDialog(null, "Enter Customer Name: ");
        		try {
					sorts(ba.searchCustomerByName(userInput));
				} catch (CustomerNotFoundException e1) {
//					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Customer with the given Name does not exist, try again", "Error", JOptionPane.ERROR_MESSAGE);
				}
        	}
        });
        searchByNameButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        searchByNameButton.setBounds(836, 316, 140, 35);
        getContentPane().add(searchByNameButton);

        // Set frame properties
        setSize(1000, 400);
        setLocationRelativeTo(null);
        
        
    }
    public void sorts(int c) {
    	CustomerTableModel tableModel = new CustomerTableModel(ba.displaySortedCustomer(c));
        getContentPane().setLayout(null);

        // Create the JTable
        JTable table = new JTable(tableModel);

        // Create a scroll pane and add the table to it
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 450));
        scrollPane.setSize(new Dimension(800, 450));
        scrollPane.setBounds(0, 0, 1000, 306);
        scrollPane.setFont(new Font("Tahoma", Font.PLAIN, 15));

        // Add the scroll pane to the frame
        getContentPane().add(scrollPane);
        
       
    }
    public void sorts(List<Customer>custs) {
    	CustomerTableModel tableModel = new CustomerTableModel(custs);
    	getContentPane().setLayout(null);
    	
    	// Create the JTable
    	JTable table = new JTable(tableModel);
    	
    	// Create a scroll pane and add the table to it
    	JScrollPane scrollPane = new JScrollPane(table);
    	scrollPane.setPreferredSize(new Dimension(800, 450));
    	scrollPane.setSize(new Dimension(800, 450));
    	scrollPane.setBounds(0, 0, 1000, 306);
    	scrollPane.setFont(new Font("Tahoma", Font.PLAIN, 15));
    	
    	// Add the scroll pane to the frame
    	getContentPane().add(scrollPane);
    	
    	
    }
    // Custom TableModel for Customer data
    private static class CustomerTableModel extends AbstractTableModel {

        private List<Customer> customerList;
        private String[] columnNames = {"Customer ID", "Name", "Age","Sex", "Mobile","Email", "Passport", "Account Number", "Balance", "Opening Date"};

        public CustomerTableModel(HashMap<Integer, Customer> customerData) {
            this.customerList = new ArrayList<>(customerData.values());
        }
        public CustomerTableModel(List<Customer>customerList) {
        	this.customerList=customerList;
        }

        @Override
        public int getRowCount() {
            return customerList.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Customer customer = customerList.get(rowIndex);
            Account account = customer.bankAccount;

            switch (columnIndex) {
                case 0:
                    return customer.custId;
                case 1:
                    return customer.custName;
                case 2:
                    return customer.custAge;
                case 3:
                	return customer.sex;
                case 4:
                    return customer.custMobile;
                case 5:
                	return customer.emailId;
                case 6:
                    return customer.custPassport;
                case 7:
                    return (account != null) ? account.accountNumber : "N/A";
                case 8:
                    return (account != null) ? account.balance : "N/A";
                case 9:
                    return (account != null) ? account.openingDate : "N/A";
                default:
                    return null;
            }
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
    }
}

