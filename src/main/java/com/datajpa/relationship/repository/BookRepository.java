package com.datajpa.relationship.repository;

import com.datajpa.relationship.model.Book;
import com.datajpa.relationship.model.ZipCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
}
