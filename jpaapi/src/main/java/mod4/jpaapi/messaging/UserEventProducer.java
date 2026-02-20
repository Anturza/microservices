package mod4.jpaapi.messaging;

import messaging.UserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class UserEventProducer {

    @Value("${app.topic-name}")
    private String topicName;

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    @Autowired
    public UserEventProducer(KafkaTemplate<String, UserEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserEvent(UserEvent event) {
        CompletableFuture<SendResult<String, UserEvent>> future = kafkaTemplate.send(topicName, event);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + event +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.err.println("Unable to send message=[" + event + "] due to : " + ex.getMessage());
            }
        });
    }
}
