package ch.hslu.swda.data.dto;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO (Data Transfer Object) representing the data required to create an order.
 */
@Serdeable
public record CreateOrderDto (
        /**
         * The article number of the product to be ordered.
         */
        @NotNull
        @NotBlank
        int articleNumber,

        /**
         * The quantity of the product to be ordered.
         */
        @NotNull
        @NotBlank
        int articleCount,

        /**
         * The contact information for the order.
         */
        @NotNull
        @NotBlank
        String orderContact
){

}
