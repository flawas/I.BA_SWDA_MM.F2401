package ch.hslu.swda.data.entities.order;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Serdeable
public final class Address {

    @NonNull
    @NotBlank
    private String firstname;

    @NonNull
    @NotBlank
    private String lastname;

    @NonNull
    @NotBlank
    private String address1;

    private String address2;

    private String address3;

    @NonNull
    @NotBlank
    private String city;

    @NonNull
    @NotBlank
    private String zipCode;

    public Address() { }
    public Address(
            final String firstname,
            final String lastname,
            final String address1,
            final String address2,
            final String address3,
            final String city,
            final String zipCode
    ) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.city = city;
        this.zipCode = zipCode;
    }
}