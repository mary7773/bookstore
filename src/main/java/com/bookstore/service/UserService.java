package com.bookstore.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.bookstore.domain.security.UserRole;
import com.bookstore.models.User;
import com.bookstore.models.UserBilling;
import com.bookstore.models.UserPayment;
import com.bookstore.models.UserShipping;

public interface UserService {
	
	  User createUser(User user, Set<UserRole> userRole);

	    User findByUsername(String username);

	    User findByEmail(String email);

	    Optional<User> findById(Long id);

	    Iterable<User> findAll();

	    User save(User user);

	    String findByUsernameAndPassword(String username, String passsword);

	    void updateUserPaymentInfo(UserBilling userBilling, UserPayment userPayment, User user);

	    void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user);

	    void setUserDefaultPayment(Long PaymentId, User user);

	    void updateUserShipping(UserShipping userShipping, User user);

	    void setUserDefaultShipping(Long ShippingId, User user);
	
}
