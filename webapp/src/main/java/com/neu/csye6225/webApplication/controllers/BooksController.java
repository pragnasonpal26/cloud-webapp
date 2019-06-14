package com.neu.csye6225.webApplication.controllers;

import java.util.List;
import java.util.Optional;

import com.neu.csye6225.webApplication.entity.Images;
import com.neu.csye6225.webApplication.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.neu.csye6225.webApplication.entity.Books;
import com.neu.csye6225.webApplication.service.BooksService;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class BooksController {

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
	public Books getBooks(@PathVariable Long id) {
		Optional<Books> singleBook = booksService.getBooks(id);
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
	public ResponseEntity<String> deleteBooks(@PathVariable Long id) {
		booksService.deleteBooks(id);
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
	public Images getCoverImage(@PathVariable Long idBook, @PathVariable Long idImage) {
		Optional<Books> singleBook = booksService.getBooks(idBook);
		Images image = singleBook.get().getImage();
		return image;
	}

	@PostMapping("/book/{idBook}/image")
	@ResponseStatus(HttpStatus.OK)
	public Images postImage(@RequestParam("file") MultipartFile file, @PathVariable Long idBook) {
		Optional<Books> singleBook = booksService.getBooks(idBook);
		Images image = singleBook.get().getImage();
		imagesService.storeFile(file,image.getUrl());
		return image;
	}

	@PutMapping("/book/{idBook}/image/{idImage}")
	public Images updateImage(@PathVariable Long idBook, @PathVariable Long idImage, @RequestParam("file") MultipartFile file) {
		Books singleBook = booksService.getBooks(idBook).get();
		Images image = singleBook.getImage();
		imagesService.deleteFile(image.getUrl());
		imagesService.storeFile(file,image.getUrl());
		return image;
	}

	@DeleteMapping("/book/{idBook}/image/{idImage}")
	public ResponseEntity<String> deleteImage(@PathVariable Long idImage, @PathVariable Long idBook) {
		Books singleBook = booksService.getBooks(idBook).get();
		Images image = singleBook.getImage();
		imagesService.deleteFile(image.getUrl());
		image.setImageId("");
		image.setUrl("");
		imagesService.update(image);
		return new ResponseEntity("Deleted successfully!", HttpStatus.OK);
	}

}