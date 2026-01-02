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

import com.green.order.domain.Customer;
import com.green.order.service.CustomerService;
import com.green.webstoremodels.dto.CustomerDto;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<CustomerDto> list() {
        return customerService.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> get(@PathVariable Integer id) {
        Customer c = customerService.findById(id);
        return c != null ? ResponseEntity.ok(toDto(c)) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CustomerDto> create(@RequestBody CustomerDto customer) {
        Customer saved = customerService.save(toEntity(customer, null));
        return ResponseEntity.ok(toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> update(@PathVariable Integer id, @RequestBody CustomerDto customer) {
        Customer existing = customerService.findById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        Customer saved = customerService.save(toEntity(customer, existing));
        return ResponseEntity.ok(toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private CustomerDto toDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getCustomerId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setPassword(customer.getPassword());
        return dto;
    }

    private Customer toEntity(CustomerDto dto, Customer existing) {
        Customer entity = existing != null ? existing : new Customer();
        entity.setCustomerId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        // Preserve password/verification flags when present to avoid breaking auth-related data.
        if (existing != null) {
            entity.setPassword(existing.getPassword());
            entity.setVerified(existing.getVerified());
            entity.setVerificationCode(existing.getVerificationCode());
        } else if (entity.getPassword() == null) {
            entity.setPassword("");
            entity.setVerified(Boolean.FALSE);
        }
        return entity;
    }
}
