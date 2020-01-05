package com.bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.models.BookToCartItem;
import com.bookstore.models.CartItem;

public interface BookToCartItemRepository extends CrudRepository<BookToCartItem, Long>{
	void deleteByCartItem(CartItem cartItem);
}
