package ch.hslu.swda.data.dto;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) representing an order.
 */
@Serdeable
public record OrderDto(
        /**
         * The order number.
         */
        @NotNull
        @NotBlank
        int orderNumber,

        /**
         * The article number of the ordered product.
         */
        @NotNull
        @NotBlank
        int articleNumber,

        /**
         * The quantity of the ordered product.
         */
        @NotNull
        @NotBlank
        int articleCount,

        /**
         * The delivery date of the ordered product.
         */
        @NonNull
        @NotBlank
        LocalDate articleDeliveryDate,

        /**
         * The date when the order was placed.
         */
        @NotNull
        @NotBlank
        LocalDate orderDate,

        /**
         * The contact information for the order.
         */
        @NotNull
        @NotBlank
        String orderContact

) {
}
