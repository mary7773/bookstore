package com.bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.models.BillingAddress;

public interface BillingAddressRepository extends CrudRepository<BillingAddress, Long>{

}
