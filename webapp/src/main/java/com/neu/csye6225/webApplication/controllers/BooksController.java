package com.neu.csye6225.webApplication.controllers;

import java.awt.*;
import java.net.URL;
import java.util.List;

import java.util.Optional;
import java.util.UUID;

import com.neu.csye6225.webApplication.entity.Images;
import com.neu.csye6225.webApplication.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.neu.csye6225.webApplication.entity.Books;
import com.neu.csye6225.webApplication.Utilities.AmazonUtil;
import com.neu.csye6225.webApplication.service.BooksService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class BooksController {
	
	@Autowired
	private AmazonClient amazonClient;

	@Autowired
	BooksController(AmazonClient amazonClient) {
	    this.amazonClient = amazonClient;
	}


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
		List<Books> books = booksService.getBooks();
		return books;
	}

	@GetMapping("/books/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Books getBooks(@PathVariable String id) {
//		if(booksService.getBooks(UUID.fromString(id)))
		Optional<Books> singleBook = booksService.getBooks(UUID.fromString(id));
		if (!singleBook.isPresent())
			System.out.println("Catch this and display no such Books etc.");
		return singleBook.get();

	}

	@PostMapping("/books")
	@ResponseStatus(HttpStatus.CREATED)
	public Books postBooks(@RequestBody Books postBooks) {
		imagesService.update(postBooks.getImage());
		return booksService.saveBooks(postBooks);
	}

	@DeleteMapping("/books/{id}")
	public ResponseEntity<String> deleteBooks(@PathVariable String id) {
		booksService.deleteBooks(UUID.fromString(id));
		return new ResponseEntity("Deleted successfully!", HttpStatus.NO_CONTENT);
	}

	@PutMapping("/books")
	public ResponseEntity<Books> updateBook(@RequestBody Books postBook) {
		if (postBook == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		booksService.update(postBook);
		return new ResponseEntity<>(postBook, HttpStatus.NO_CONTENT);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Images createImage(@RequestBody Images image){return imagesService.saveImage(image);}

	@GetMapping("/book/{idBook}/image/{idImage}")
	@ResponseStatus(HttpStatus.OK)
	public URL getCoverImage(@PathVariable String idBook, @PathVariable String idImage) {
		Optional<Books> singleBook = booksService.getBooks(UUID.fromString(idBook));
		Images image = singleBook.get().getImage();
		return amazonClient.generatePreSignedURL(image.getUrl());
	}

	@PostMapping("/book/{idBook}/image")
	@ResponseStatus(HttpStatus.OK)
	public Images postImage(@RequestParam("file") MultipartFile file, @PathVariable String idBook) {
		Optional<Books> singleBook = booksService.getBooks(UUID.fromString(idBook));
		Images image = singleBook.get().getImage();
		image.setUrl(amazonClient.uploadFile(file));
		imagesService.update(image);
		return image;
	}

	@PutMapping("/book/{idBook}/image/{idImage}")
	public Images updateImage(@PathVariable String idBook, @PathVariable String idImage, @RequestParam("file") MultipartFile file) {
		Books singleBook = booksService.getBooks(UUID.fromString(idBook)).get();
		Images image = singleBook.getImage();
		amazonClient.deleteFileFromS3Bucket(image.getUrl());
		image.setUrl(amazonClient.uploadFile(file));
		imagesService.update(image);
		return image;
	}

	@DeleteMapping("/book/{idBook}/image/{idImage}")
	public ResponseEntity<String> deleteImage(@PathVariable String idImage, @PathVariable String idBook) {
		Books singleBook = booksService.getBooks(UUID.fromString(idBook)).get();
		Images image = singleBook.getImage();
		amazonClient.deleteFileFromS3Bucket(image.getUrl());
		singleBook.setImage(null);
		booksService.update(singleBook);
		//imagesService.deleteImages(UUID.fromString(idImage));
		return new ResponseEntity("Deleted successfully!", HttpStatus.OK);
	}

}
