package com.simon.wigellpadel.service;

import com.simon.wigellpadel.entity.Address;
import com.simon.wigellpadel.exception.AddressDoesNotExistException;
import com.simon.wigellpadel.exception.CustomerDoesNotExistException;
import com.simon.wigellpadel.repository.AddressRepository;
import com.simon.wigellpadel.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;
    private final Logger logger = LoggerFactory.getLogger(AddressService.class);

    public AddressService(AddressRepository addressRepository, CustomerRepository customerRepository) {
        this.addressRepository  = addressRepository;
        this.customerRepository = customerRepository;
    }

    public void postAddress(Address address, Long customerId) {
        customerRepository.findById(customerId).orElseThrow(() -> {
            logger.warn("Customer with id {} not found - creation attempt rejected", customerId);
            throw new CustomerDoesNotExistException(customerId);
        });

        Address savedAddress = addressRepository.save(address);
        logger.info("Created address with id: {} for customer id: {}", savedAddress.getId(), customerId);
    }

    public void deleteAddress(Address address) {
        addressRepository.delete(address);
        logger.info("Deleted address with id: {} from customer with id: {}", address.getId(), address.getCustomer().getId());
    }
}