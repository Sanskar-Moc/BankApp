package com.Bank.frontend;

import javax.swing.JFrame;
import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import com.Bank.BankAccount.BankApp;
import javax.swing.JRadioButton;
import java.awt.Color;

public class CustomerForm extends JFrame {
	private JTextField nameField;
	private JTextField mobileField;
	private JTextField passportField;
	private JTextField dobField;
	BankApp ba;
	private JTextField emailField;
	private JTextField monthField;
	private JTextField dayField;
	private String sex;
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getSex() {
		return sex;
	}

	public CustomerForm(BankApp ba) {
		getContentPane().setBackground(Color.WHITE);
		this.ba=ba;
		setResizable(false);
		setSize(new Dimension(600, 500));
		setPreferredSize(new Dimension(600, 500));
		setName("CustomerFormFrame");
		setLocationByPlatform(true);
		setTitle("Customer Form");
		getContentPane().setLayout(null);
		
		JButton BackButton = new JButton("Back");
		BackButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		BackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MainMenu();
			}
		});
		BackButton.setBounds(133, 361, 91, 43);
		getContentPane().add(BackButton);
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(149, 57, 91, 32);
		getContentPane().add(lblNewLabel);
		
		JLabel lblMobileNo = new JLabel("Mobile No.");
		lblMobileNo.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMobileNo.setBounds(149, 101, 91, 32);
		getContentPane().add(lblMobileNo);
		
		JLabel lblPassportId = new JLabel("Passport ID");
		lblPassportId.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPassportId.setBounds(149, 184, 91, 32);
		getContentPane().add(lblPassportId);
		
		nameField = new JTextField();
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		nameField.setBounds(257, 58, 178, 32);
		getContentPane().add(nameField);
		nameField.setColumns(10);
		
		mobileField = new JTextField();
		mobileField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		mobileField.setColumns(10);
		mobileField.setBounds(257, 102, 178, 32);
		getContentPane().add(mobileField);
		
		passportField = new JTextField();
		passportField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		passportField.setColumns(10);
		passportField.setBounds(257, 185, 178, 32);
		getContentPane().add(passportField);
		
		JLabel lblPassportId_1 = new JLabel("DOB");
		lblPassportId_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPassportId_1.setBounds(149, 226, 91, 32);
		getContentPane().add(lblPassportId_1);
		
		dobField = new JTextField();
		dobField.setText("YYYY");
		dobField.setToolTipText("YYYY-MM-DD");
		dobField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		dobField.setColumns(10);
		dobField.setBounds(257, 227, 66, 32);
		getContentPane().add(dobField);
		
		JLabel lblPassportId_1_1 = new JLabel("Please enter in the given format YYYY-MM-DD");
		lblPassportId_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPassportId_1_1.setBounds(183, 255, 339, 32);
		getContentPane().add(lblPassportId_1_1);
		
		JButton createButton = new JButton("Create");
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name= nameField.getText();
				long mobile=!mobileField.getText().isEmpty()?Long.parseLong(mobileField.getText()):0L;
				String pass=passportField.getText();
				String year=dobField.getText();
				String month=monthField.getText();
				String day=dayField.getText();
				String email=emailField.getText();
				LocalDate date=LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
				System.out.println(name.length());
				if(nameField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(CustomerForm.this, "Name Field is empty", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(mobileField.getText().length()!=10) {
					JOptionPane.showMessageDialog(CustomerForm.this, "Mobile Number should have 10 digits", "Error", JOptionPane.ERROR_MESSAGE);
					mobileField.setText("");
					return;
				}
				if(!ba.isValidDate(date, new int[] {0})) {
					JOptionPane.showMessageDialog(CustomerForm.this, "Invalid Date or Age < 10", "Error", JOptionPane.ERROR_MESSAGE);
					dobField.setText("");
					return;	
				}
				if(!CustomerForm.isValidEmail(email)) {
					JOptionPane.showMessageDialog(CustomerForm.this, "Invalid Email", "Error", JOptionPane.ERROR_MESSAGE);
					emailField.setText("");
					return;
				}
				if(passportField.getText().isEmpty() || !ba.checkPassport(pass)) {
					JOptionPane.showMessageDialog(CustomerForm.this, "Passport with following ID already exist", "Error", JOptionPane.ERROR_MESSAGE);
					passportField.setText("");
					return;
				}
				ba.createNewCustomer(getSex(),name,mobile,pass,date,email);
				JOptionPane.showMessageDialog(CustomerForm.this, "Custmer Created Succesfully", "OK", JOptionPane.INFORMATION_MESSAGE);
				ba.serialize();
				
				dispose();
				new MainMenu();
			}
		});
		createButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		createButton.setBounds(333, 361, 102, 43);
		getContentPane().add(createButton);
		
		JLabel lblPassportId_1_2 = new JLabel("EmailID");
		lblPassportId_1_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPassportId_1_2.setBounds(149, 304, 91, 32);
		getContentPane().add(lblPassportId_1_2);
		
		emailField = new JTextField();
		emailField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		emailField.setColumns(10);
		emailField.setBounds(257, 305, 178, 32);
		getContentPane().add(emailField);
		
		
		JRadioButton femaleRadioButton = new JRadioButton("Female");
		femaleRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JRadioButton selectedRadioButton = (JRadioButton) e.getSource();
                String selectedValue = selectedRadioButton.getText();
//                System.out.println(selectedValue);
                setSex(selectedValue);
			}
		});
		femaleRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		femaleRadioButton.setBounds(359, 153, 95, 21);
		getContentPane().add(femaleRadioButton);
		
		JRadioButton maleRadioButton = new JRadioButton("Male");
		maleRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JRadioButton selectedRadioButton = (JRadioButton) e.getSource();
                String selectedValue = selectedRadioButton.getText();
//                System.out.println(selectedValue);
                setSex(selectedValue);
			}
		});
		maleRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		maleRadioButton.setBounds(257, 153, 95, 21);
		getContentPane().add(maleRadioButton);
		setVisible(true);
		
		ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(maleRadioButton);
        buttonGroup.add(femaleRadioButton);
        
        JLabel lblGender = new JLabel("Gender");
        lblGender.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblGender.setBounds(149, 142, 91, 32);
        getContentPane().add(lblGender);
        
        monthField = new JTextField();
        monthField.setToolTipText("YYYY-MM-DD");
        monthField.setText("MM");
        monthField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        monthField.setColumns(10);
        monthField.setBounds(343, 227, 35, 32);
        getContentPane().add(monthField);
        
        dayField = new JTextField();
        dayField.setToolTipText("YYYY-MM-DD");
        dayField.setText("DD");
        dayField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        dayField.setColumns(10);
        dayField.setBounds(400, 227, 35, 32);
        getContentPane().add(dayField);
        
        JLabel lblNewLabel_1 = new JLabel("/");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel_1.setBounds(331, 227, 18, 32);
        getContentPane().add(lblNewLabel_1);
        
        JLabel lblNewLabel_1_1 = new JLabel("/");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel_1_1.setBounds(386, 227, 18, 32);
        getContentPane().add(lblNewLabel_1_1);
	}
	public static boolean isValidEmail(String email) {
        // Define a regular expression for a basic email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Create a Pattern object
        Pattern pattern = Pattern.compile(emailRegex);

        // Create a matcher object
        Matcher matcher = pattern.matcher(email);

        // Return true if the email matches the pattern, false otherwise
        return matcher.matches();
    }
}
