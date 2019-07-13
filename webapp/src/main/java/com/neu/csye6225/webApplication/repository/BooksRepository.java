package com.neu.csye6225.webApplication.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.neu.csye6225.webApplication.entity.Books;

public interface BooksRepository extends JpaRepository<Books, Long> {
	Optional<Books> findById(UUID id);

	void deleteById(UUID id);
}
