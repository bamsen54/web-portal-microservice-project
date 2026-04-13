package com.simon.wigellpadel.mapper;

import com.simon.wigellpadel.dto.AddressDto;
import com.simon.wigellpadel.dto.CustomerDto;
import com.simon.wigellpadel.dto.PostAddressDto;
import com.simon.wigellpadel.entity.Address;
import com.simon.wigellpadel.entity.Customer;

public class AddressMapper {

    public static AddressDto toDto(Address address) {
        if (address == null)
            return null;

        return new AddressDto(
                address.getId(),
                address.getStreet(),
                address.getPostalCode(),
                address.getCity()
        );
    }

    public static Address fromDto(AddressDto dto) {
        if (dto == null)
            return null;

        Address address = new Address();
        address.setStreet(dto.street());
        address.setCity(dto.city());
        address.setPostalCode(dto.postalCode());

        return address;
    }

    public static Address fromPostDto(PostAddressDto dto) {

        if (dto == null)
            return null;

        Address address = new Address();
        address.setStreet(dto.street());
        address.setCity(dto.city());
        address.setPostalCode(dto.postalCode());

        return address;
    }
}
