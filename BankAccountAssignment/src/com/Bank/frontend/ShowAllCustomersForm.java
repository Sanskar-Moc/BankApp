package com.Bank.frontend;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import com.Bank.BankAccount.BankApp;
import com.Bank.BankAccount.Accounts.Account;
import com.Bank.BankAccount.customers.Customer;
import com.Bank.database.JDBCUtils;
import com.Bank.exceptions.CustomerNotFoundException;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
//        CustomerTableModel tableModel = new CustomerTableModel(customerData);
//        getContentPane().setLayout(null);
//
//        // Create the JTable
//        
//        
//        
//        
//        
//        
//        
//        JTable table = new JTable(tableModel);
        sorts(2);

        // Create a scroll pane and add the table to it
//        JScrollPane scrollPane = new JScrollPane(table);
//        scrollPane.setPreferredSize(new Dimension(800, 450));
//        scrollPane.setSize(new Dimension(800, 450));
//        scrollPane.setBounds(0, 0, 1000, 306);
//        scrollPane.setFont(new Font("Tahoma", Font.PLAIN, 15));
//
//        // Add the scroll pane to the frame
//        getContentPane().add(scrollPane);
        
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
        		//					sorts(ba.searchCustomerByName(userInput),userInput);
				sorts(userInput);
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
        
        DefaultTableModel model = new DefaultTableModel();
        
        
        // Create the JTable
        JTable table = new JTable(model);

        // Create a scroll pane and add the table to it
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 450));
        scrollPane.setSize(new Dimension(800, 450));
        scrollPane.setBounds(0, 0, 1000, 306);
        scrollPane.setFont(new Font("Tahoma", Font.PLAIN, 15));

        // Add the scroll pane to the frame
        getContentPane().add(scrollPane);
        
        try {
			ResultSet resultSet=null;
			switch(c) {
			case 1:
				resultSet=JDBCUtils.selectData("name");
				break;
			case 2:
				resultSet=JDBCUtils.selectData("custId");
				break;
			case 3:
				resultSet=JDBCUtils.selectData("balance");
			}
			
			ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Add column names to the table model
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(metaData.getColumnName(i));
            }

            // Add rows to the table model
            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = resultSet.getObject(i);
                }
                model.addRow(rowData);
            }
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        	
       
    }
//    public void sorts(List<Customer>custs,String user) {
    	public void sorts(String user) {
//    	CustomerTableModel tableModel = new CustomerTableModel(custs);
    	getContentPane().setLayout(null);
        
    	DefaultTableModel model = new DefaultTableModel();

    	// Create the JTable
    	JTable table = new JTable(model);
    	
    	// Create a scroll pane and add the table to it
    	JScrollPane scrollPane = new JScrollPane(table);
    	scrollPane.setPreferredSize(new Dimension(800, 450));
    	scrollPane.setSize(new Dimension(800, 450));
    	scrollPane.setBounds(0, 0, 1000, 306);
    	scrollPane.setFont(new Font("Tahoma", Font.PLAIN, 15));
    	
    	// Add the scroll pane to the frame
    	getContentPane().add(scrollPane);
    	
    	
    	try {
			ResultSet resultSet=JDBCUtils.selectDataSearch(user);
			
			ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Add column names to the table model
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(metaData.getColumnName(i));
            }

            // Add rows to the table model
            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = resultSet.getObject(i);
                }
                model.addRow(rowData);
            }
    	
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
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

