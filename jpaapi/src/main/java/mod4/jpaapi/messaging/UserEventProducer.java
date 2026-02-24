package mod4.jpaapi.messaging;

import messaging.UserEvent;
import mod4.jpaapi.exceptionhandling.UsersAPIExceptionHandler;
import org.apache.kafka.common.errors.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class UserEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(UserEventProducer.class);

    @Value("${app.topic-name}")
    private String topicName;

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    @Autowired
    public UserEventProducer(KafkaTemplate<String, UserEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserEvent(UserEvent event) {
        CompletableFuture<SendResult<String, UserEvent>> future;

        try {
            future = kafkaTemplate.send(topicName, event);
        } catch (TimeoutException e) {
            logger.error("Timeout occurred while sending message to Kafka: {}", e.getMessage());
            return;
        }

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("Sent message=[{}] with offset=[{}]", event, result.getRecordMetadata().offset());
            } else {
                logger.error("Unable to send message=[{}] due to : {}", event, ex.getMessage());
            }
        });
    }
}
