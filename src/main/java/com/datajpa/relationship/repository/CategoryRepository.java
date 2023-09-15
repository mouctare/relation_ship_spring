package com.datajpa.relationship.repository;

import com.datajpa.relationship.model.Category;
import com.datajpa.relationship.model.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

}
