package com.neu.csye6225.webApplication.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neu.csye6225.webApplication.entity.Books;
import com.neu.csye6225.webApplication.repository.BooksRepository;


@Service
public class BooksService {

	@Autowired
	private BooksRepository booksRepository;

	@Transactional
	public List<Books> getBooks() {
		return booksRepository.findAll();
	}

	public Books saveBooks(Books postBooks) {
		return booksRepository.save(postBooks);
	}

	public void deleteBooks(UUID id) {
		booksRepository.deleteById(id);
	}

	public void update(Books book) {
		booksRepository.save(book);
	}

	@Transactional
	public Optional<Books> getBooks(UUID fromString) {
		return booksRepository.findById(fromString);
	}
}