package com.bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.models.UserBilling;

public interface UserBillingRepository extends CrudRepository<UserBilling, Long> {

}
