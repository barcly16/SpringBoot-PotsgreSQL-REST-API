package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Address;
import com.example.postgresdemo.model.Employee;
import com.example.postgresdemo.repository.AddressRepository;
import com.example.postgresdemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AddressController {
    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("/address")
    public Page<Address> getAddress(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }

    @PostMapping("/address")
    public Address createAddress(@Valid @RequestBody Address address) {
        return addressRepository.save(address);
    }

    @PutMapping("/address/{addressId}")
    public Address updateAddress(@PathVariable Integer addressId,
                                   @Valid @RequestBody Address addressRequest) {
        return addressRepository.findById(addressId)
                .map(address -> {
                    address.setStreet(addressRequest.getStreet());
                    address.setHouseNumber(addressRequest.getHouseNumber());
                    address.setCity(addressRequest.getCity());
                    address.setZipCode(addressRequest.getZipCode());
                    return addressRepository.save(address);
                }).orElseThrow(() -> new ResourceNotFoundException("Address not found with id " + addressId));
    }

    @DeleteMapping("/address/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable Integer addressId) {
        return addressRepository.findById(addressId)
                .map(address -> {
                    addressRepository.delete(address);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Address not found with id " + addressId));
    }

}
