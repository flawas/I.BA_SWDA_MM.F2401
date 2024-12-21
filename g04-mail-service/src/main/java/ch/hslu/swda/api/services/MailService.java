package ch.hslu.swda.api.services;

import ch.hslu.swda.data.dto.CreateMailDto;

public interface MailService {

    void sendMail(CreateMailDto createMailDto);
}
