package com.neu.csye6225.webApplication.repository;

import com.neu.csye6225.webApplication.entity.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {

}
