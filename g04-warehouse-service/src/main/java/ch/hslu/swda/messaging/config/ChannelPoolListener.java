package ch.hslu.swda.messaging.config;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.micronaut.rabbitmq.connect.ChannelInitializer;
import jakarta.inject.Singleton;

import java.io.IOException;


/**
 * A singleton class responsible for initializing RabbitMQ channels.
 * This class extends {@link io.micronaut.rabbitmq.connect.ChannelInitializer} and implements
 * channel initialization logic for specific queues and exchanges.
 */
@Singleton
public final class ChannelPoolListener extends ChannelInitializer {
    /**
     * Initializes the RabbitMQ channel with necessary exchanges, queues, and bindings.
     *
     * @param channel The RabbitMQ channel to be initialized.
     * @param name    The name of the channel.
     * @throws IOException If an I/O error occurs while initializing the channel.
     */
    @Override
    public void initialize(final Channel channel, final String name) throws IOException {
        // Declare exchange for warehouse orders
        channel.exchangeDeclare("fbs.warehouse", BuiltinExchangeType.TOPIC, true);
        channel.exchangeDeclare("fbs.order", BuiltinExchangeType.TOPIC, true);
        channel.exchangeDeclare("fbs.inventory", BuiltinExchangeType.TOPIC, true);

        // Declare queue and bind it to exchange for validated inventory messages
        channel.queueDeclare("WarehouseQ", true, false, false, null);
        channel.queueBind("WarehouseQ", "fbs.warehouse", "warehouse.*");
    }
}
