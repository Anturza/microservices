package study.mailapp.services;

import messaging.UserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UsersConsumerService {

    private final EmailService emailService;

    @Autowired
    public UsersConsumerService(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "test-topic", groupId = "notification-group")
    public void listenJson(UserEvent event) {
        System.out.printf("Received notification: %s, %s\n", event.getEventDescription(), event.getUserEmail());
        //further process
        //add in database, and then it tries to send with scheduled repeat if needed
        emailService.sendSimpleEmail(event.getUserEmail(), "Информация от тестового сервиса", event.getEventDescription());
    }
}
