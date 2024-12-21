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
        channel.exchangeDeclare("fbs.log", BuiltinExchangeType.TOPIC, true);
        channel.exchangeDeclare("fbs.order", BuiltinExchangeType.TOPIC, true);


        channel.queueDeclare("LogReceivedQ", true, false, false, null);
        channel.queueBind("LogReceivedQ", "fbs.log", "log.*");

        channel.queueDeclare("OrderStatusChangedQ", true, false, false, null);
        channel.queueBind("OrderStatusChangedQ", "fbs.order", "order.status-changed.*");
    }
}