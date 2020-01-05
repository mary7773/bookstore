package com.bookstore.service;

import com.bookstore.models.UserShipping;

public interface UserShippingService {
	
	UserShipping findById(Long id);
	
	void removeById(Long id);

}
