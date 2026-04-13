package com.wigell.sushi.controller;

import com.wigell.sushi.dto.AddressDTO;
import com.wigell.sushi.dto.CustomerDTO;
import com.wigell.sushi.entity.Address;
import com.wigell.sushi.entity.Customer;
import com.wigell.sushi.service.AddressService;
import com.wigell.sushi.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final AddressService addressService;

    public CustomerController(CustomerService customerService, AddressService addressService) {
        this.customerService = customerService;
        this.addressService = addressService;
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody Customer customer) {
        CustomerDTO saved = customerService.createCustomer(customer);
        return ResponseEntity.created(URI.create("/api/v1/customers/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.updateCustomer(id, customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{customerId}/addresses")
    public ResponseEntity<AddressDTO> addAddress(@PathVariable int customerId, @RequestBody Address address) {
        AddressDTO saved = addressService.addAddress(customerId, address);
        return ResponseEntity.created(URI.create("/api/v1/customers/" + customerId + "/addresses/" + saved.getId())).body(saved);
    }

    @DeleteMapping("/{customerId}/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable int customerId, @PathVariable int addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }
}