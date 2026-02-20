package study.mailapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.mailapp.services.EmailService;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-email")
    public String sendEmailTest() {
        emailService.sendSimpleEmail("anturza@gmail.com", "Test Subject", "Hello World! This is a test email from Spring Boot.");
        return "Email sent!";
    }
}
