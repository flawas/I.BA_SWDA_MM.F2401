package ch.hslu.swda.data.mappers;

import ch.hslu.swda.data.dto.payment.PaymentDto;
import ch.hslu.swda.data.entities.payment.Payment;

public final class PaymentMappers {

    public static Payment toEntity(final PaymentDto paymentDto) {
        if (paymentDto == null)
            return null;

        return new Payment(
            paymentDto.number(),
            paymentDto.orderNumber(),
            paymentDto.customerEmail(),
            paymentDto.state()
        );
    }

    public static PaymentDto toDto(final Payment payment) {
        if (payment == null)
            return null;

        return new PaymentDto(
            payment.getNumber(),
            payment.getOrderNumber(),
            payment.getCustomerEmail(),
            payment.getInvoiceDate(),
            payment.getState()
        );
    }
}