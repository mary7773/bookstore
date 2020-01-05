package com.bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.models.ShippingAddress;

public interface ShippingAddressRepository extends CrudRepository<ShippingAddress, Long> {
	
}
