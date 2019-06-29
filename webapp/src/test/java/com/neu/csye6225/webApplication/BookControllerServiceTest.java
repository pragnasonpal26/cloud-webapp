//package com.neu.csye6225.webApplication;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import java.awt.print.Book;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import com.neu.csye6225.webApplication.controllers.*;
//import com.neu.csye6225.webApplication.entity.*;
//import com.neu.csye6225.webApplication.service.BooksService;
//
//
//public class BookControllerServiceTest extends AbstractTest {
//	@Autowired
//	private BooksService booksService;
//   @Override
//   @Before
//   public void setUp() {
//      super.setUp();
//   }
//   @Test
//   public void getBooks() throws Exception {
//      String uri = "api/books";
//      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
//         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
//
//      int status = mvcResult.getResponse().getStatus();
//      assertEquals(200, status);
//      String content = mvcResult.getResponse().getContentAsString();
//      Book[] booklist = super.mapFromJson(content, Book[].class);
//      assertTrue(booklist.length > 0);
//   }
//
//   @Test
//   public void postBooks() throws Exception {
//      String uri = "api/books";
//      List<Books> books = booksService.getBooks();
//      String inputJson = super.mapToJson(books);
//      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
//         .contentType(MediaType.APPLICATION_JSON_VALUE)
//         .content(inputJson)).andReturn();
//
//      int status = mvcResult.getResponse().getStatus();
//      assertEquals(201, status);
//      String content = mvcResult.getResponse().getContentAsString();
//      assertEquals(content, "Book is created successfully");
//   }
//   @Test
//   public void updateBook() throws Exception {
//      String uri = "api/books";
//      Book book = new Book();
//      String inputJson = super.mapToJson(book);
//      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
//         .contentType(MediaType.APPLICATION_JSON_VALUE)
//         .content(inputJson)).andReturn();
//
//      int status = mvcResult.getResponse().getStatus();
//      assertEquals(200, status);
//      String content = mvcResult.getResponse().getContentAsString();
//      assertEquals(content, "Book is updated successsfully");
//   }
//   @Test
//   public void deleteBooks(String id) throws Exception {
//      String uri = "api/books/{id}";
//      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
//      int status = mvcResult.getResponse().getStatus();
//      assertEquals(200, status);
//      String content = mvcResult.getResponse().getContentAsString();
//      assertEquals(content, "Book is deleted successsfully");
//   }
//}