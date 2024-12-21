package ch.hslu.swda.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    @Test
    void testGenerateRandomText() {
        assertNotEquals("asdf", StringUtil.generateRandomText(5));

    }

    @Test
    void testGenerateRandomTextLength() {
        assertEquals(10, StringUtil.generateRandomText(10).length());
    }
}