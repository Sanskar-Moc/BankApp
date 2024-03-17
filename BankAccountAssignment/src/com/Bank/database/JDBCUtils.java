package com.Bank.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {
	
	public JDBCUtils() {
		 
	}
	public static Connection getCon() throws SQLException {
		String url = null;
		String user = null;
		String password = null;
		Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("src\\com\\Bank\\database\\db.properties");

            // Load the properties file
            prop.load(input);

            // Get the property values
            url= prop.getProperty("url");
            user = prop.getProperty("user");
            password = prop.getProperty("password");

            
//            System.out.println("Database: " + url);
//            System.out.println("DB User: " + user);
//            System.out.println("DB Password: " + password);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		Connection conn=DriverManager.getConnection(url,user,password);
		return conn;
	}
	public static boolean checkCustomer(int id) throws SQLException {
		String query="select count(*) as c from customer where custId="+id+";";

			Connection con=getCon();
			Statement stat=con.createStatement();
			ResultSet rs=stat.executeQuery(query);
			rs.next();
			return rs.getInt("c")>0;
	}
	public static void insertCustomer(int custId, String custName,int custAge,long custMobile,
			String custPassport,
			String DOB,String emailId,String sex) throws SQLException {
//		String insert="INSERT INTO dept VALUES("+id+",'"+dep.toUpperCase()+"','"+loc.toUpperCase()+"')";
		Connection con=getCon();
//		Statement stat=con.createStatement();
		PreparedStatement pstat=con.prepareStatement("INSERT INTO Customer (custId, custName, custAge, custMobile, custPassport, accountAdded, DOB, emailId, sex)\r\n"
				+ "VALUES (?, ?, ?, ?, ?, FALSE, ?, ?, ?);\r\n"
				+ ""); //?  = PLACEHOLDER
		pstat.setInt(1, custId);
		pstat.setString(2, custName.toUpperCase());
		pstat.setInt(3, custAge);
		pstat.setLong(4, custMobile);
		pstat.setString(5, custPassport);
		pstat.setString(6, DOB);
		pstat.setString(7, emailId);
		pstat.setString(8, sex);
		boolean n =pstat.execute();
//		int n =stat.executeUpdate(insert);
		System.out.println("rows inserted :"+!n);
	}
	public static void insertAccount(long acno,double bal,String date) throws SQLException {
//		String insert="INSERT INTO dept VALUES("+id+",'"+dep.toUpperCase()+"','"+loc.toUpperCase()+"')";
		Connection con=getCon();
//		Statement stat=con.createStatement();
		PreparedStatement pstat=con.prepareStatement("INSERT INTO Accounts (accNo, balance, openingDate)\r\n"
				+ "VALUES (?, ?, ?);\r\n"
				+ ""
				+ ""); //?  = PLACEHOLDER
		pstat.setLong(1, acno);
		pstat.setDouble(2, bal);
		pstat.setString(3, date);

		boolean n =pstat.execute();
//		int n =stat.executeUpdate(insert);
		System.out.println("rows inserted :"+!n);
	}
	public static void insertSaving(long acno,double min,boolean sal) throws SQLException {
//		String insert="INSERT INTO dept VALUES("+id+",'"+dep.toUpperCase()+"','"+loc.toUpperCase()+"')";
		Connection con=getCon();
//		Statement stat=con.createStatement();
		PreparedStatement pstat=con.prepareStatement("INSERT INTO SavingsAccount (isSalaryAccount, minBal, accNo)\r\n"
				+ "VALUES (?, ?, ?);"); //?  = PLACEHOLDER
		pstat.setBoolean(1, sal);
		pstat.setDouble(2, min);
		pstat.setLong(3, acno);

		boolean n =pstat.execute();
//		int n =stat.executeUpdate(insert);
		System.out.println("rows inserted :"+!n);
	}
	public static void insertFD(long acno,double amt,int y) throws SQLException {
		Connection con=getCon();
//		Statement stat=con.createStatement();
		PreparedStatement pstat=con.prepareStatement("INSERT INTO FixedDeposit (depositAmount, tenure,  accNo)\r\n"
				+ "VALUES (?,?,?);"); //?  = PLACEHOLDER
		pstat.setDouble(1, amt);
		pstat.setInt(2, y);
		pstat.setLong(3, acno);

		boolean n =pstat.execute();
//		int n =stat.executeUpdate(insert);
		System.out.println("rows inserted :"+!n);
	}
	public static void updateRecord(int id,long accno ) throws SQLException {
		Connection con=getCon();
		PreparedStatement pstat=con.prepareStatement("UPDATE Customer\r\n"
				+ "		SET accountAdded = TRUE, accNo = ? \r\n"
				+ "		WHERE custId = ?;"); //?  = PLACEHOLDER
//		pstat.setString(1, what);
		pstat.setLong(1, accno);
		
		pstat.setInt(2, id);
		boolean n =pstat.execute();
		System.out.println("record updated :"+!n);
	}
	public static void updateRefs(String sym,long ref ) throws SQLException {
		Connection con=getCon();
		PreparedStatement pstat=con.prepareStatement("UPDATE inits\r\n"
				+ "		SET ref=? \r\n"
				+ "		WHERE symbol = ?;"); //?  = PLACEHOLDER
//		pstat.setString(1, what);
		pstat.setLong(1, ref);
		
		pstat.setString(2, sym);
		boolean n =pstat.execute();
		System.out.println("record updated :"+!n);
	}
	public static void updateInterest(String which,double intr,long acno ) throws SQLException {
		Connection con=getCon();
		PreparedStatement pstat=con.prepareStatement("UPDATE "+which+"\r\n"
				+ "		SET interest=? \r\n"
				+ "		WHERE accNo = ?;"); //?  = PLACEHOLDER
//		pstat.setString(1, what);
		pstat.setDouble(1, intr);
		
		pstat.setLong(2, acno);
		boolean n =pstat.execute();
		System.out.println("record updated :"+!n);
	}
	public static ResultSet selectData(String what) throws SQLException {
		String query="select custId as id,custName as name,custAge as age,custMobile as mobile, ifnull(customer.accNo,'N/A') as Acno ,emailId as email, sex as gender,ifnull(balance,'N/A')as balance, ifnull(openingDate,'N/A') as opening\r\n"
				+ "from customer left join accounts on customer.accNo=accounts.accNo order by "+what+";";
		Connection con=getCon();
		Statement stat=con.createStatement();
		ResultSet rs=stat.executeQuery(query);
		return rs;
	}
	public static ResultSet selectDataSearch(String what) throws SQLException {
		String query="select custId as id,custName as name,custAge as age,custMobile as mobile, ifnull(customer.accNo,'N/A') as Acno ,emailId as email, sex as gender,ifnull(balance,'N/A')as balance, ifnull(openingDate,'N/A') as opening\r\n"
				+ "from customer left join accounts on customer.accNo=accounts.accNo where custName like '%"+what+"%';";
		Connection con=getCon();
		Statement stat=con.createStatement();
		ResultSet rs=stat.executeQuery(query);
		return rs;
	}
	public static int selectRef() throws SQLException {
		String query="select ref from inits where symbol like '#'";
		Connection con=getCon();
		Statement stat=con.createStatement();
		ResultSet rs=stat.executeQuery(query);
		rs.next();
		return rs.getInt("ref");
	}
	public static long selectRef$() throws SQLException {
		String query="select ref from inits where symbol like '*'";
		Connection con=getCon();
		Statement stat=con.createStatement();
		ResultSet rs=stat.executeQuery(query);
		rs.next();
		return rs.getLong("ref");
	}
}
