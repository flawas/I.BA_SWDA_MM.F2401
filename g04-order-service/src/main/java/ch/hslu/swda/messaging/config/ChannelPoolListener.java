package ch.hslu.swda.messaging.config;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.micronaut.rabbitmq.connect.ChannelInitializer;
import jakarta.inject.Singleton;

import java.io.IOException;

@Singleton
public final class ChannelPoolListener extends ChannelInitializer {
    @Override
    public void initialize(final Channel channel, final String name) throws IOException {
        channel.exchangeDeclare("fbs.order", BuiltinExchangeType.TOPIC, true);
        channel.exchangeDeclare("fbs.inventory", BuiltinExchangeType.TOPIC, true);
        channel.exchangeDeclare("fbs.payment", BuiltinExchangeType.TOPIC, true);
        channel.exchangeDeclare("mail.send", BuiltinExchangeType.TOPIC, true);

        /* CONSUME */
        // TODO-go: Fix general queue (not working due to enum serialization)
        // channel.queueDeclare("ValidatedPositionQ", true, false, false, null);
        // channel.queueBind("ValidatedPositionQ", "fbs.inventory", "order.validated-order-position.*");

        channel.queueDeclare("ValidatedPositionReorderedQ", true, false, false, null);
        channel.queueBind("ValidatedPositionReorderedQ", "fbs.inventory", "order.validated-order-position.reordered");

        channel.queueDeclare("ValidatedPositionFaultyQ", true, false, false, null);
        channel.queueBind("ValidatedPositionFaultyQ", "fbs.inventory", "order.validated-order-position.faulty");

        channel.queueDeclare("ValidatedPositionReadyQ", true, false, false, null);
        channel.queueBind("ValidatedPositionReadyQ", "fbs.inventory", "order.validated-order-position.ready");

        // TODO-go: Fix general queue (not working due to enum serialization)
        // channel.queueDeclare("CheckedCustomerQ", true, false, false, null);
        // channel.queueBind("CheckedCustomerQ", "fbs.payment", "order.checked-customer.*");

        channel.queueDeclare("CheckedCustomerPendingQ", true, false, false, null);
        channel.queueBind("CheckedCustomerPendingQ", "fbs.payment", "order.checked-customer.pending");

        channel.queueDeclare("CheckedCustomerCompletedQ", true, false, false, null);
        channel.queueBind("CheckedCustomerCompletedQ", "fbs.payment", "order.checked-customer.completed");

        // TODO-go: Remove temporary payment messaging (to payment ms)

        channel.queueDeclare("CheckCustomerQ", true, false, false, null);
        channel.queueBind("CheckCustomerQ", "fbs.order", "payment.check-customer");

        channel.queueDeclare("GeneratePaymentQ", true, false, false, null);
        channel.queueBind("GeneratePaymentQ", "fbs.order", "order.status-changed.ready");
    }
}
