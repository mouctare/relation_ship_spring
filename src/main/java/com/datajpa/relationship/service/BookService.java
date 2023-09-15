package com.datajpa.relationship.service;

import com.datajpa.relationship.dto.requestDto.BookRequestDto;
import com.datajpa.relationship.dto.requestDto.responseDto.BookResponseDto;
import com.datajpa.relationship.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {

    BookResponseDto addBook(BookRequestDto bookRequestDto);
    List<BookResponseDto> getBooks();
    BookResponseDto getBookById(Long bookId);
    Book getBook(Long bookId);
    BookResponseDto deleteBook(Long bookId);
    BookResponseDto editBook(Long bookId, BookRequestDto bookRequestDto);
    BookResponseDto addAuthorToBook(Long bookId, Long authorId);
    BookResponseDto deteleAuthorFromBook(Long bookId, Long authorId);
    BookResponseDto addCategoryToBook(Long bookId, Long categoryId);
    BookResponseDto removeCategoryFromBook(Long bookId, Long categoryId);
}
