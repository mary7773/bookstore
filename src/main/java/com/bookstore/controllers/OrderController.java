package com.bookstore.controllers;

import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.models.Order;
import com.bookstore.models.User;
import com.bookstore.service.UserService;

/**
 * @author Marieta
 *
 */
@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/getOrderList")
	public Set<Order> getOrderList(Principal principal) {
		User user = userService.findByUsername(principal.getName());
		Set<Order> orderList = user.getOrderList();		
		return orderList;
	}

}
