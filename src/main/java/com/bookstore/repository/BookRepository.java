package com.bookstore.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.models.Book;

public interface BookRepository extends CrudRepository<Book, Long>{
	List<Book> findByTitleContaining(String keyword);
}
