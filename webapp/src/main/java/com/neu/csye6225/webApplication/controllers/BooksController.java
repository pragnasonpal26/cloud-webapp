package com.neu.csye6225.webApplication.controllers;

import java.net.URL;
import java.util.List;

import java.util.Optional;
import java.util.UUID;

import com.neu.csye6225.webApplication.entity.Images;
import com.neu.csye6225.webApplication.service.ImagesService;
import com.timgroup.statsd.StatsDClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.neu.csye6225.webApplication.entity.Books;
import com.neu.csye6225.webApplication.Utilities.AmazonUtil;
import com.neu.csye6225.webApplication.service.BooksService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
public class BooksController {

	private final static Logger logger = LoggerFactory.getLogger(BooksController.class);

	@Autowired
	private StatsDClient statsDClient;

	@Autowired
	private AmazonUtil amazonClient;

	@Autowired
	BooksController(AmazonUtil amazonClient) {
		this.amazonClient = amazonClient;
	}

	@Autowired
	private BooksService booksService;

	@Autowired
	private ImagesService imagesService;

	@GetMapping("/books")
	@ResponseStatus(HttpStatus.OK)
	public List<Books> getBooks() {
		statsDClient.incrementCounter("endpoint.books.http.get");
		logger.info("Inside Get Books: start");
		List<Books> books = booksService.getBooks();
		logger.info("Inside Get Books: Success!");
		logger.info("Inside Get books: Stop");
		return books;
	}

	@GetMapping("/books/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Books getBooks(@PathVariable String id) {
//		if(booksService.getBooks(UUID.fromString(id)))
		logger.info("Inside Get books: start");
		statsDClient.incrementCounter("endpoint.books.http.get.book");
		Optional<Books> singleBook = booksService.getBooks(UUID.fromString(id));
		if (!singleBook.isPresent())
			System.out.println("Catch this and display no such Books etc.");
		logger.info("Inside Get books: No books found!");
		logger.info("Inside Get books: Success!");
		logger.info("Inside Get books: Stop");
		return singleBook.get();

	}

	@PostMapping("/books")
	@ResponseStatus(HttpStatus.CREATED)
	public Books postBooks(@RequestBody Books postBooks) {
		logger.info("Inside Upload Books: start");
		statsDClient.incrementCounter("endpoint.book.http.post");
		imagesService.update(postBooks.getImage());
		logger.info("Inside Upload Books: Success!");
		logger.info("Inside Upload Books: Stop");
		return booksService.saveBooks(postBooks);
	}

	@DeleteMapping("/books/{id}")
	public ResponseEntity<String> deleteBooks(@PathVariable String id) {
		logger.info("Inside Delete Books: start");
		statsDClient.incrementCounter("endpoint.book.http.delete");
		booksService.deleteBooks(UUID.fromString(id));
		logger.info("Inside Delete Books: Success");
		logger.info("Inside Delete Books: Stop");
		return new ResponseEntity("Deleted successfully!", HttpStatus.NO_CONTENT);
	}

	@PutMapping("/books")
	public ResponseEntity<Books> updateBook(@RequestBody Books postBook) {
		logger.info("Inside Delete Books: start");
		statsDClient.incrementCounter("endpoint.book.http.put");
		if (postBook == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		logger.info("Inside Delete Books: Not found");
		booksService.update(postBook);
		logger.info("Inside Delete Books: Success!");
		logger.info("Inside Delete Books: Stop");
		return new ResponseEntity<>(postBook, HttpStatus.NO_CONTENT);
	}
}
