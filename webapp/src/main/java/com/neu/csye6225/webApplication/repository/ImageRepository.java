package com.neu.csye6225.webApplication.repository;

import com.neu.csye6225.webApplication.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Images, Long> {

    Optional<Images> findById(String id);

    void deleteById(UUID id);
}