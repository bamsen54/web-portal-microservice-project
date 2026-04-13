package com.wigell.sushi.service;

import com.wigell.sushi.dto.CustomerDTO;
import com.wigell.sushi.entity.Customer;
import com.wigell.sushi.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private CustomerDTO toDTO(Customer c) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(c.getId());
        dto.setUsername(c.getUsername());
        dto.setFirstName(c.getFirstName());
        dto.setLastName(c.getLastName());
        return dto;
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(this::toDTO).toList();
    }

    public CustomerDTO createCustomer(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        Customer saved = customerRepository.save(customer);
        logger.info("admin created customer {}", saved.getId());
        return toDTO(saved);
    }

    public CustomerDTO updateCustomer(int id, Customer customer) {
        customer.setId(id);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        Customer saved = customerRepository.save(customer);
        logger.info("admin updated customer {}", id);
        return toDTO(saved);
    }

    public void deleteCustomer(int id) {
        customerRepository.deleteById(id);
        logger.info("admin deleted customer {}", id);
    }
}