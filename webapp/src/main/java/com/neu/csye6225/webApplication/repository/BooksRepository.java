package com.neu.csye6225.webApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.neu.csye6225.webApplication.entity.Books;

public interface BooksRepository extends JpaRepository<Books, Long> {
	
	// JPA does not support UNION. Hence Native query
		String findQuery = "SELECT * FROM books WHERE book_name LIKE %?1% UNION SELECT * FROM books WHERE content LIKE %?1%";
		@Query(value = findQuery, nativeQuery = true)
		List<Books> findByQueryString(String queryString);

}
