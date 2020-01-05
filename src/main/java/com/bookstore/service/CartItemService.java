package com.bookstore.service;

import java.util.List;

import com.bookstore.models.Book;
import com.bookstore.models.CartItem;
import com.bookstore.models.ShoppingCart;
import com.bookstore.models.User;

public interface CartItemService {
	
	CartItem addBookToCartItem(Book book, User user, int qty);
	
	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
	
//	List<CartItem> findByOrder(Order order);
	
	CartItem updateCartItem(CartItem cartItem);
	
	void removeCartItem(CartItem cartItem);
	
	CartItem findById(Long id);
	
	CartItem save(CartItem cartItem);

}
