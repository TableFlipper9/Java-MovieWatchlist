package model.service.notification;

import java.util.Properties;

public class EmailService {

    private final String senderEmail = "your_email@gmail.com";
    private final String appPassword = "your_app_password";

    public void sendEmail(String to, String subject, String content) {
        System.out.println("\n--- EMAIL LOG ---");
        System.out.println("To      : " + to);
        System.out.println("Subject : " + subject);
        System.out.println("-----------------");
        System.out.println(content);
        System.out.println("-----------------\n");
    }
}