package com.Bank.BankAccount;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class FileStorageDao {
	public void serialize(HashMap<Integer,Customer> customerData,HashSet<String> passportData, HashMap<String,List<Long>> nameToId,List<Customer> customers) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("C:\\C376\\data.dat")))) {
            oos.writeObject(customerData);
            oos.writeObject(passportData);
            oos.writeObject(nameToId);
            oos.writeObject(customers);
            System.out.println("Objects serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
//	public void deserialize(HashMap<Integer,Customer> customerData,HashSet<String> passportData, HashMap<String,Long> nameToId,List<Customer> customers) {
//		try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("C:\\C376\\data.dat")))) {
//            // Read the serialized objects
//            Object obj1 = ois.readObject();
//            Object obj2 = ois.readObject();
//            Object obj3 = ois.readObject();
//            Object obj4 = ois.readObject();
//
//            // Check if the deserialized objects are of the expected types
//            if (obj1 instanceof HashMap && obj2 instanceof HashSet && obj3 instanceof HashMap && obj4 instanceof List) {
//                // Cast the objects back to their respective types
////                @SuppressWarnings("unchecked")
//                customerData = (HashMap<Integer, Customer>) obj1;
//
////                @SuppressWarnings("unchecked")
//                passportData = (HashSet<String>) obj2;
//
////                @SuppressWarnings("unchecked")
//                nameToId = (HashMap<String, Long>) obj3;
//                
//                customers=(ArrayList<Customer>)obj4;
//
//                // Display the deserialized objects
//                System.out.println("Deserialized Customer Data: " + customerData);
//                System.out.println("Deserialized Passport Data: " + passportData);
//                System.out.println("Deserialized Name to ID Mapping: " + nameToId);
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//	}
}
