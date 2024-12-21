package ch.hslu.swda.api.services;

import ch.hslu.swda.data.dto.payment.PaymentDto;
import io.micronaut.core.annotation.NonNull;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    List<PaymentDto> getAll();
    Optional<PaymentDto> findByNumber(@NonNull final String paymentNumber);
    List<PaymentDto> findByCustomerEmail(@NonNull final String customerEmail);

    void handleGeneratePayment(@NonNull final String orderNumber, @NonNull final String customerEmail);
}
