package com.san.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.san.domain.Book;
import com.san.repo.BookRepository;

@Component
public class BookService {

	@Autowired
	private BookRepository productRepository;

	public void insertThousandsBooks() {
		Random random = new Random();
		for (int i = 0; i < 1000; i++) {
			productRepository.save(new Book("book" + i, "author" + i, 2000 + random.nextInt(15)));
		}
	}

}
