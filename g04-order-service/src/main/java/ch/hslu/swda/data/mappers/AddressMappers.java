package ch.hslu.swda.data.mappers;

import ch.hslu.swda.data.dto.order.AddressDto;
import ch.hslu.swda.data.entities.order.Address;

public final class AddressMappers {
    public static Address toEntity(final AddressDto AddressDto) {
        if (AddressDto == null)
            return null;

        return new Address(
            AddressDto.firstname(),
            AddressDto.lastname(),
            AddressDto.address1(),
            AddressDto.address2(),
            AddressDto.address3(),
            AddressDto.city(),
            AddressDto.zipCode()
        );
    }

    public static AddressDto toDto(final Address Address) {
        if (Address == null)
            return null;

        return new AddressDto(
            Address.getFirstname(),
            Address.getLastname(),
            Address.getAddress1(),
            Address.getAddress2(),
            Address.getAddress3(),
            Address.getCity(),
            Address.getZipCode()
        );
    }
}
