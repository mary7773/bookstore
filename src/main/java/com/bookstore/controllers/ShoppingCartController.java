package com.bookstore.controllers;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.models.Book;
import com.bookstore.models.CartItem;
import com.bookstore.models.ShoppingCart;
import com.bookstore.models.User;
import com.bookstore.service.BookService;
import com.bookstore.service.CartItemService;
import com.bookstore.service.ShoppingCartService;
import com.bookstore.service.UserService;

/**
 * @author Marieta
 *
 */
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@RequestMapping("/add")
	public ResponseEntity<String> addItem (
			@RequestBody HashMap<String, String> mapper, Principal principal
			){
		String bookId = (String) mapper.get("bookId");
		String qty = (String) mapper.get("qty");
		
		User user = userService.findByUsername(principal.getName());
		Book book = bookService.findOne(Long.parseLong(bookId));
		
		if(Integer.parseInt(qty) > book.getInStockNumber()) {
			return new ResponseEntity("Not Enough Stock!", HttpStatus.BAD_REQUEST);
		}
		
		CartItem cartItem = cartItemService.addBookToCartItem(book, user, Integer.parseInt(qty));
		
		return new ResponseEntity("Book Added Successfully!", HttpStatus.OK);
	}
	
	@RequestMapping("/getCartItemList")
	public List<CartItem> getCartItemList(Principal principal) {
		User user = userService.findByUsername(principal.getName());
		ShoppingCart shoppingCart = user.getShoppingCart();
		
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		
		shoppingCartService.updateShoppingCart(shoppingCart);
		
		return cartItemList;
	}
	
	@RequestMapping("/getShoppingCart")
	public ShoppingCart getShoppingCart(Principal principal) {
		User user = userService.findByUsername(principal.getName());
		ShoppingCart shoppingCart = user.getShoppingCart();
		
		shoppingCartService.updateShoppingCart(shoppingCart);
		
		return shoppingCart;
	}
	
	@RequestMapping("/removeItem")
	public ResponseEntity<String> removeItem(@RequestBody String id) {
		cartItemService.removeCartItem(cartItemService.findById(Long.parseLong(id)));
		
		return new ResponseEntity<String>("Cart Item Removed Successfully!", HttpStatus.OK);
	}
	
	@RequestMapping("/updateCartItem")
	public ResponseEntity<String> updateCartItem(
			@RequestBody HashMap<String, String> mapper
			){
		String cartItemId = mapper.get("cartItemId");
		String qty = mapper.get("qty");
		
		CartItem cartItem = cartItemService.findById(Long.parseLong(cartItemId));
		cartItem.setQty(Integer.parseInt(qty));
		
		cartItemService.updateCartItem(cartItem);
		
		return new ResponseEntity<String>("Cart Item Updated Successfully!", HttpStatus.OK);
	}
	
}
