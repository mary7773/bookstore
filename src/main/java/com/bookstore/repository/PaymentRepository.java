package com.bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.models.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Long>{

}
