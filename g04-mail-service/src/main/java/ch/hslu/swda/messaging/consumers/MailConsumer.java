package ch.hslu.swda.messaging.consumers;


import ch.hslu.swda.api.services.MailService;
import ch.hslu.swda.data.dto.CreateMailDto;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RabbitListener
public final class MailConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(MailConsumer.class);

    private final MailService mailService;

    public MailConsumer(MailService mailService) {
        this.mailService = mailService;
    }


    @Queue("MailQ")
    public void consume(final CreateMailDto createMailDto) {
        LOG.info("MQ-CONSUMER: Consumed new order from ({}).", createMailDto.mailHeaderTitle());
        mailService.sendMail(createMailDto);
    }
}
