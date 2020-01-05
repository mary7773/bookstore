package com.bookstore.service;

import com.bookstore.models.UserPayment;

public interface UserPaymentService {
	UserPayment findById(Long id);
	
	void removeById(Long id);
}
