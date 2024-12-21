package ch.hslu.swda.entities;

import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

public class Article {

    private int articleNumber;
    private String deliveryDate;

    public Article() {
    }

    public void setArticleNumber(final int articleNumber) {
        this.articleNumber = articleNumber;
    }

    public void setDeliveryDate(final LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate.toString();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return articleNumber == article.articleNumber && Objects.equals(deliveryDate, article.deliveryDate);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(articleNumber, deliveryDate);
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleNumber=" + articleNumber +
                ", deliveryDate='" + deliveryDate + '\'' +
                '}';
    }
}
