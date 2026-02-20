package study.mailapp.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UsersConsumerService {

    @KafkaListener(topics = "test-topic", groupId = "notification-group")
    public void listenJson(UserEvent event) {
        System.out.printf("Received notification: %s, %s", event.getEventDescription(), event.getUserEmail());
        //further process
    }
}
