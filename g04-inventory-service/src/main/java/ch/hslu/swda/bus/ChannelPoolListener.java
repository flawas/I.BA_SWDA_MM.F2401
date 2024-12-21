package ch.hslu.swda.bus;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.micronaut.rabbitmq.connect.ChannelInitializer;
import jakarta.inject.Singleton;

import java.io.IOException;

@Singleton
public class ChannelPoolListener extends ChannelInitializer {

    @Override
    public void initialize(Channel channel, final String name) throws IOException {
        channel.exchangeDeclare("fbs.inventory", BuiltinExchangeType.TOPIC, true);
        channel.exchangeDeclare("fbs.order", BuiltinExchangeType.TOPIC, true);
        channel.exchangeDeclare("fbs.log", BuiltinExchangeType.TOPIC, true);
        channel.exchangeDeclare("fbs.warehouse", BuiltinExchangeType.TOPIC, true);

        // CONSUME
        channel.queueDeclare("ValidatePositionQ", true, false, false, null);
        channel.queueBind("ValidatePositionQ", "fbs.order", "inventory.validate-order-position");
    }
}
