package ch.hslu.swda.data.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MailDtoTest {

    MailDto mailDto = new MailDto(
            "flavio.waser@stud.hslu.ch",
            "Header",
            "Inhalt"
    );

    @Test
    void mailAddress() {
        assertEquals("flavio.waser@stud.hslu.ch", mailDto.mailAddress());
    }

    @Test
    void mailHeaderTitle() {
        assertEquals("Header", mailDto.mailHeaderTitle());
    }

    @Test
    void mailText() {
        assertEquals("Inhalt", mailDto.mailText());
    }
}