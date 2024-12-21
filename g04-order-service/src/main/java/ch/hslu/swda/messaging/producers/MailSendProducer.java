package ch.hslu.swda.messaging.producers;

import ch.hslu.swda.data.dto.mail.CreateMailDto;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.micronaut.rabbitmq.annotation.RabbitProperty;

@RabbitClient("mail.send")
@RabbitProperty(name = "contentType", value = "application/json")
public interface MailSendProducer {
    @Binding("mail.send")
    void produce(final CreateMailDto createMailDto);
}
