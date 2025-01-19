package app.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentDto {
    private BigDecimal sum;
    private String createdAt;
    private String orderId;
    private String userId;
}
