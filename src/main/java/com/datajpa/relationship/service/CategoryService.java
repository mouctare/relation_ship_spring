package com.datajpa.relationship.service;

import com.datajpa.relationship.dto.requestDto.responseDto.CategoryResponseDto;
import com.datajpa.relationship.model.Category;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    Category getCategory(Long categoryId);
}
