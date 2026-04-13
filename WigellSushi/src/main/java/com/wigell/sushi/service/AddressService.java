package com.wigell.sushi.service;

import com.wigell.sushi.dto.AddressDTO;
import com.wigell.sushi.entity.Address;
import com.wigell.sushi.entity.Customer;
import com.wigell.sushi.repository.AddressRepository;
import com.wigell.sushi.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    public AddressService(AddressRepository addressRepository, CustomerRepository customerRepository) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    private AddressDTO toDTO(Address a) {
        AddressDTO dto = new AddressDTO();
        dto.setId(a.getId());
        dto.setStreet(a.getStreet());
        dto.setCity(a.getCity());
        dto.setPostalCode(a.getPostalCode());
        if (a.getCustomer() != null) {
            dto.setCustomerId(a.getCustomer().getId());
        }
        return dto;
    }

    public AddressDTO addAddress(int customerId, Address address) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        address.setCustomer(customer);
        Address saved = addressRepository.save(address);
        logger.info("admin added address {} to customer {}", saved.getId(), customerId);
        return toDTO(saved);
    }

    public void deleteAddress(int addressId) {
        addressRepository.deleteById(addressId);
        logger.info("admin deleted address {}", addressId);
    }
}