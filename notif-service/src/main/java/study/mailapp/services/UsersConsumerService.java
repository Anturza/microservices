package study.mailapp.services;

import jakarta.validation.Valid;
import messaging.UserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UsersConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(UsersConsumerService.class);

    private final EmailService emailService;

    @Autowired
    public UsersConsumerService(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "${app.topic-name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenJson(@Valid UserEvent event) {
        logger.info("Received notification: '{}', '{}'\n", event.getEventDescription(), event.getUserEmail());
        //further process
        //add in database, and then it tries to send with scheduled repeat if needed
        emailService.sendSimpleEmail(event.getUserEmail(), "Информация от тестового сервиса", event.getEventDescription());
    }

}
