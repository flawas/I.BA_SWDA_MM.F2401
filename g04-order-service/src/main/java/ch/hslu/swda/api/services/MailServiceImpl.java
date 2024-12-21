package ch.hslu.swda.api.services;

import ch.hslu.swda.data.dto.mail.CreateMailDto;
import ch.hslu.swda.data.dto.order.OrderDto;
import ch.hslu.swda.messaging.producers.MailSendProducer;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;
import jakarta.validation.constraints.Email;

@Singleton
public class MailServiceImpl implements MailService {

    private final MailSendProducer mailSendProducer;

    public MailServiceImpl(final MailSendProducer mailSendProducer) {
        this.mailSendProducer = mailSendProducer;
    }

    @Override
    public void handleSendStatusMail(
            @NonNull final OrderDto orderDto,
            @NonNull @Email final String toEmail,
            @NonNull final String headerStatus,
            @NonNull final String bodyContent
    ) {
        mailSendProducer.produce(
                new CreateMailDto(
                        toEmail,
                        String.format("Order %s %s", orderDto.number(), headerStatus),
                        bodyContent
                )
        );
    }

    @Override
    public void handleSendReadyMail(@NonNull final OrderDto orderDto) {
        handleSendStatusMail(
                orderDto,
                orderDto.customerEmail(),
                "is ready",
                String.format("Your Order %s from %s is ready.",
                        orderDto.number(),
                        orderDto.orderDate())
        );
    }

    @Override
    public void handleSendNeedsApprovalMail(@NonNull final OrderDto orderDto) {
        handleSendStatusMail(
                orderDto,
                orderDto.sellerEmail(),
                "needs approval",
                String.format("The Order %s from %s needs your approval. The customer %s has pending payments.",
                        orderDto.number(),
                        orderDto.orderDate(),
                        orderDto.customerEmail()
                )
        );

        handleSendStatusMail(
                orderDto,
                orderDto.customerEmail(),
                "needs approval",
                String.format("The Order %s from %s needs approval due to pending payments. Pay them now or contact your seller %s.",
                        orderDto.number(),
                        orderDto.orderDate(),
                        orderDto.sellerEmail()
                )
        );
    }
}
