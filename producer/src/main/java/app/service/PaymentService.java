package app.service;

import app.dto.PaymentDto;

import java.util.concurrent.ExecutionException;

public interface PaymentService {
    String createAsync(PaymentDto paymentDto);

    String createNoAsync(PaymentDto paymentDto) throws ExecutionException, InterruptedException;
}
