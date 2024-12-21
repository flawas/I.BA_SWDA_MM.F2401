package ch.hslu.swda.api.services;

import ch.hslu.swda.common.util.EmailUtil;
import ch.hslu.swda.data.dto.CreateLogDto;
import ch.hslu.swda.data.dto.CreateMailDto;
import ch.hslu.swda.data.entities.Service;
import ch.hslu.swda.messaging.producers.BusinessEventLogProducer;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.time.Instant;
import java.util.Properties;

@Singleton
public class MailServiceImpl implements MailService{
    private static final Logger LOG = LoggerFactory.getLogger(MailServiceImpl.class);
    private final BusinessEventLogProducer businessEventLogProducer;

    public MailServiceImpl(BusinessEventLogProducer businessEventLogProducer) {
        this.businessEventLogProducer = businessEventLogProducer;
    }


    @Override
    public void sendMail(CreateMailDto createMailDto) {
        final String fromEmail = "swda-g04@flawas.ch";
        final String password = "Q2zOkkkHfA1rN#-%Q2zOkkkHfA1rN#-%";
        final String toEmail = createMailDto.mailAddress();

        LOG.debug("Mail SSL Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "asmtp.mail.hostpoint.ch"); //SMTP Host
        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", "465"); //SMTP Port

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        Session session = Session.getDefaultInstance(props, auth);
        LOG.debug("Mail Session created");
        EmailUtil.sendEmail(session, toEmail, createMailDto.mailHeaderTitle(), createMailDto.mailText());
        businessEventLogProducer.produce(
                new CreateLogDto(
                        Instant.now(),
                        null,
                        Service.MAIL,
                        null,
                        "Sent mail to: " + createMailDto.mailAddress() + " with title " + createMailDto.mailHeaderTitle()
                                + " with text " + createMailDto.mailText()
                ));
    }
}
