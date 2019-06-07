package com.neu.csye6225.webApplication.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.neu.csye6225.webApplication.entity.Books;
import com.neu.csye6225.webApplication.service.BooksService;

import javax.validation.Valid;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class BooksController {

	@Autowired
	private BooksService booksService;

	@GetMapping("/books")
	@ResponseStatus(HttpStatus.OK)
	public List<Books> getBooks() {
		List<Books> books = booksService.getBooks();
		return books;
	}

	@GetMapping("/books/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Books getBooks(@PathVariable Long id) {
		Optional<Books> singleBook = booksService.getBooks(id);
		if (!singleBook.isPresent())
			System.out.println("Catch this and display no such Books etc.");
		return singleBook.get();

	}

	@PostMapping("/books")
	@ResponseStatus(HttpStatus.CREATED)
	public Books postBooks(@RequestBody Books postBooks) {
		return booksService.saveBooks(postBooks);
	}

	@DeleteMapping("/books/delete/{id}")
	public ResponseEntity<String> deleteBooks(@PathVariable Long id) {
		booksService.deleteBooks(id);
		return new ResponseEntity("Deleted successfully!", HttpStatus.OK);
	}

	@PutMapping("/books")
	public ResponseEntity<Books> updateBook(@RequestBody Books postBook) {
		if (postBook == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		booksService.update(postBook);
		return new ResponseEntity<>(postBook, HttpStatus.NO_CONTENT);
	}

}