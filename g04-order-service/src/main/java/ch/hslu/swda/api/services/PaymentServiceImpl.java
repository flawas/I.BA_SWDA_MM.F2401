package ch.hslu.swda.api.services;

import ch.hslu.swda.api.repositories.PaymentRepository;
import ch.hslu.swda.data.dto.payment.PaymentDto;
import ch.hslu.swda.data.entities.payment.Payment;
import ch.hslu.swda.data.mappers.PaymentMappers;
import jakarta.inject.Singleton;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

@Singleton
public final class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(final PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<PaymentDto> getAll() {
        return paymentRepository.findAll().stream()
                .map(PaymentMappers::toDto)
                .toList();
    }

    @Override
    public Optional<PaymentDto> findByNumber(@NonNull final String paymentNumber) {
        return paymentRepository.findByNumber(paymentNumber).map(PaymentMappers::toDto);
    }

    @Override
    public List<PaymentDto> findByCustomerEmail(@NonNull final String customerEmail) {
        return paymentRepository.findByCustomerEmail(customerEmail).stream()
                .map(PaymentMappers::toDto)
                .toList();
    }

    @Override
    public void handleGeneratePayment(
            @NonNull final String orderNumber,
            @NonNull final String customerEmail) {
        paymentRepository.save(new Payment(orderNumber, customerEmail));
    }
}
