package ch.hslu.swda.data.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateMailDtoTest {

    CreateMailDto createMailDto = new CreateMailDto(
            "flavio.waser@stud.hslu.ch",
            "Testmail",
            "Inhalt"
    );

    @Test
    void mailAddress() {
        assertEquals("flavio.waser@stud.hslu.ch", createMailDto.mailAddress());
    }

    @Test
    void mailHeaderTitle() {
        assertEquals("Testmail", createMailDto.mailHeaderTitle());
    }

    @Test
    void mailText() {
        assertEquals("Inhalt", createMailDto.mailText());
    }
}