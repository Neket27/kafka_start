package app.handler;

import app.event.PaymentCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventHandler {

    @KafkaListener(topics = "payment-created-event-topic")
    public void handle(PaymentCreatedEvent paymentCreatedEvent) {
        System.out.println("Payment created event: " + paymentCreatedEvent.toString());
    }
}
