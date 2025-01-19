package app.controller;

import app.dto.PaymentDto;
import app.exception.ErrorMessage;
import app.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/paymentAsync")
    public ResponseEntity<String> createAsync(@RequestBody PaymentDto paymentDto){
        String paymentId = paymentService.createAsync(paymentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentId);
    }

    @PostMapping("/paymentNoAsync")
    public ResponseEntity<Object> create(@RequestBody PaymentDto paymentDto) {
        String paymentId;
        try {
            paymentId = paymentService.createNoAsync(paymentDto);
        }catch (ExecutionException | InterruptedException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(e.getMessage(),new Date()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentId);
    }


}
