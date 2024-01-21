package com.Bank.BankAccount;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {

    public static void run(String[] args) {
        // Sender's no-reply email address
        String from = "noreply@example.com";
        // Recipient's email address
        String to = "recipient.email@example.com";
        // Sender's Gmail username and password
        final String username = "your.email@gmail.com";
        final String password = "your-email-password";

        // Setup mail server properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create a session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);

            // Set From: header field of the header
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Acknowledgment Email");

            // Now set the actual message
            message.setText("Thank you for your submission. We have received your request.");

            // Send the message
            Transport.send(message);

            System.out.println("Acknowledgment email sent successfully.");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
