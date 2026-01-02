package com.green.order.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.green.order.domain.Address;
import com.green.order.domain.Customer;
import com.green.order.service.AddressService;
import com.green.order.service.CustomerService;
import com.green.webstoremodels.dto.AddressDto;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<AddressDto> list() {
        return addressService.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> get(@PathVariable Integer id) {
        Address a = addressService.findById(id);
        return a != null ? ResponseEntity.ok(toDto(a)) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<AddressDto> create(@RequestBody AddressDto address) {
        Address saved = addressService.save(toEntity(address, null));
        return ResponseEntity.ok(toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> update(@PathVariable Integer id, @RequestBody AddressDto address) {
        Address existing = addressService.findById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        address.setId(id);
        Address saved = addressService.save(toEntity(address, existing));
        return ResponseEntity.ok(toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private AddressDto toDto(Address address) {
        AddressDto dto = new AddressDto();
        dto.setId(address.getAddressId());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setCustomerId(address.getCustomer() != null ? address.getCustomer().getCustomerId() : null);
        return dto;
    }

    private Address toEntity(AddressDto dto, Address existing) {
        Address entity = existing != null ? existing : new Address();
        entity.setAddressId(dto.getId());
        entity.setStreet(dto.getStreet());
        entity.setCity(dto.getCity());
        if (dto.getCustomerId() != null) {
            Customer customer = customerService.findById(dto.getCustomerId());
            entity.setCustomer(customer);
        } else if (existing == null) {
            entity.setCustomer(null);
        }
        return entity;
    }
}
