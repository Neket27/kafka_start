package app.service.impl;

import app.dto.PaymentDto;
import app.event.PaymentCreatedEvent;
import app.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final KafkaTemplate<String, PaymentCreatedEvent> kafkaTemplate;

    @Override
    public String createAsync(PaymentDto paymentDto) {
        String paymentId = UUID.randomUUID().toString();
        PaymentCreatedEvent paymentCreatedEvent = new PaymentCreatedEvent(
                paymentId,
                paymentDto.getSum(),
                paymentDto.getCreatedAt(),
                paymentDto.getOrderId(),
                paymentDto.getUserId()
        );

        CompletableFuture<SendResult<String, PaymentCreatedEvent>> future = kafkaTemplate
                .send("payment-created-event-topic", paymentId, paymentCreatedEvent);


        future.whenComplete((result, exception) -> {
            if (exception != null) {
                System.out.println("Error while sending event" + exception.getMessage());
            } else {
                System.out.println("Topic " + result.getProducerRecord().topic());
                System.out.println("Partition " + result.getProducerRecord().partition());
                System.out.println("Key " + result.getProducerRecord().key());
                System.out.println("Value " + result.getProducerRecord().value());
                System.out.println("Timestamp " + result.getProducerRecord().timestamp());
            }
        });

        System.out.println("PaymentId: " + paymentId);
        return paymentId;
    }

    @Override
    public String createNoAsync(PaymentDto paymentDto) throws ExecutionException, InterruptedException {
        String paymentId = UUID.randomUUID().toString();
        PaymentCreatedEvent paymentCreatedEvent = new PaymentCreatedEvent(
                paymentId,
                paymentDto.getSum(),
                paymentDto.getCreatedAt(),
                paymentDto.getOrderId(),
                paymentDto.getUserId()
        );

        SendResult<String, PaymentCreatedEvent> result = kafkaTemplate.send("payment-created-event-topic", paymentId, paymentCreatedEvent).get();

        System.out.println("Event sent successfully" + result.getProducerRecord().toString());
        System.out.println("Topic " + result.getRecordMetadata().topic());
        System.out.println("Partition " + result.getRecordMetadata().partition());
        System.out.println("Timestamp " + result.getRecordMetadata().timestamp());
        System.out.println("Offset " + result.getRecordMetadata().offset());

        System.out.println("Event sent successfully" + result.getProducerRecord().toString());


        System.out.println("PaymentId: " + paymentId);
        return paymentId;
    }


}
