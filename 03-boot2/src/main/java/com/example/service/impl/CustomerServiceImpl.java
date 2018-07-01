package com.example.service.impl;

import com.example.persistence.entity.Customer;
import com.example.persistence.repository.CustomerRepository;
import com.example.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public Iterable<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Transactional(propagation =Propagation.REQUIRED, readOnly = true)
    @Override
    public Optional<Customer> findById(int id) {
        return customerRepository.findById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void removeById(int id) {
        customerRepository.deleteById(Integer.valueOf(id));
    }
}
