package com.example.service;

import com.example.persistence.entity.Customer;

import java.util.Optional;

public interface CustomerService {

    /**
     * 顧客を全件検索する
     */
    Iterable<Customer> findAll();

    Optional<Customer> findById(int id);

    /**
     * 1件の顧客をDBに追加する
     * @param customer 追加する顧客
     */
    void save(Customer customer);

    void removeById(int id);
}
