package com.ut.nlSystemAPi.helper;

import com.ut.nlSystemAPi.model.base.EmailSetting;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtils {

  public Boolean send(EmailSetting emailSetting, String from, String to, String subject, String body) {
    // Email Config
    if(from == null){
      from = emailSetting.getUsername();
    }
    // Mention the SMTP server address.
    String host = emailSetting.getHost();
    int port    = emailSetting.getPort();
    String username = emailSetting.getUsername();
    String password = emailSetting.getPassword();
    // Get system properties
    Properties properties = System.getProperties();
    // Setup mail server
    properties.put("mail.smtp.host", host);
    properties.put("mail.smtp.port", port);
    properties.put("mail.smtp.ssl.enable", "true");
    properties.put("mail.smtp.auth", "true");
    // Get the Session object.// and pass username and password
    Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }
    });
    // Used to debug SMTP issues
//    session.setDebug(true);
    try {
      // Create a default MimeMessage object.
      MimeMessage message = new MimeMessage(session);
      // Set From: header field of the header.
      message.setFrom(new InternetAddress(from));
      // Set To: header field of the header.
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      // Set Subject: header field
      message.setSubject(subject);
      // Now set the actual message
      message.setText(body);
      System.out.println("sending...");
      // Send message
      Transport.send(message);
      System.out.println("Sent message successfully....");
      return true;
    } catch (MessagingException mex) {
      mex.printStackTrace();
      return false;
    }
  }

}