package ch.hslu.swda.api.services;

import ch.hslu.swda.data.dto.order.OrderDto;
import io.micronaut.core.annotation.NonNull;
import jakarta.validation.constraints.Email;

public interface MailService {

    void handleSendStatusMail(
            @NonNull final OrderDto orderDto,
            @NonNull @Email final String toEmail,
            @NonNull final String headerStatus,
            @NonNull final String bodyContent
    );

    void handleSendReadyMail(@NonNull final OrderDto orderDto);

    void handleSendNeedsApprovalMail(@NonNull final OrderDto orderDto);
}
