package ch.hslu.swda.entities;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ArticleTest {

    @Test
    void setArticleNumber() {
        Article article  = new Article();
        article.setArticleNumber(100000);
        assertEquals("Article{articleNumber=100000, deliveryDate='null'}", article.toString());
    }

    @Test
    void setDeliveryDate() {
        Article article = new Article();
        article.setDeliveryDate(LocalDate.of(2024, 04, 02));
        assertEquals("Article{articleNumber=0, deliveryDate='2024-04-02'}", article.toString());
    }

    @Test
    void testEquals() {
        Article article1 = new Article();
        article1.setArticleNumber(1);
        article1.setDeliveryDate(LocalDate.of(2024, 04, 02));

        Article article2 = new Article();
        article2.setArticleNumber(1);
        article2.setDeliveryDate(LocalDate.of(2025, 05, 03));

        assertNotEquals(article1, article2);
    }

    @Test
    void testHashCode() {
        Article article = new Article();
        article.setArticleNumber(1);
        article.setDeliveryDate(LocalDate.of(2024, 04, 02));
        assertEquals(-613251266, article.hashCode());
    }

    @Test
    void testToString() {
        Article article = new Article();
        article.setArticleNumber(1);
        article.setDeliveryDate(LocalDate.of(2024, 04, 02));
        assertEquals("Article{articleNumber=1, deliveryDate='2024-04-02'}", article.toString());
    }

    @Test
    public void testEqualsContract() {
        EqualsVerifier.forClass(Article.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }
}