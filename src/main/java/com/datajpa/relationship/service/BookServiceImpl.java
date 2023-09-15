package com.datajpa.relationship.service;

import com.datajpa.relationship.dto.requestDto.BookRequestDto;
import com.datajpa.relationship.dto.requestDto.mapper;
import com.datajpa.relationship.dto.requestDto.responseDto.BookResponseDto;
import com.datajpa.relationship.model.Author;
import com.datajpa.relationship.model.Book;
import com.datajpa.relationship.model.Category;
import com.datajpa.relationship.repository.BookRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, BookService bookService, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.bookService = bookService;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Transactional
    @Override
    public BookResponseDto addBook(BookRequestDto bookRequestDto) {
        Book book = new Book();
        book.setName(bookRequestDto.getName());
        if (bookRequestDto.getAuthorIds().isEmpty()) {
            throw new IllegalArgumentException("vous avez besoin d'au moins un auteur");
        } else {
            List<Author> authors = new ArrayList();
            for (Long authorId : bookRequestDto.getAuthorIds()) {
                Author author = authorService.getAuthor(authorId);
                authors.add(author);
            }
            book.setAuthors(authors);
        }
        if (bookRequestDto.getCategoryId() == null) {
            throw new IllegalArgumentException("book atleast on category");
        }
        Category category = categoryService.getCategory(bookRequestDto.getCategoryId());
        book.setCategory(category);
        Book book1 = bookRepository.save(book);
        return mapper.bookToBokResponseDto(book1);
    }

    @Override
    public List<BookResponseDto> getBooks() {
        List<Book> books = StreamSupport
                .stream(bookRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return mapper.booksToBookResponseDtos(books);
    }

    @Override
    public BookResponseDto getBookById(Long bookId) {
        Book book = getBook(bookId);
        return mapper.bookToBokResponseDto(book);
    }

    @Override
    public Book getBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new IllegalArgumentException("cannot find book with id: " + bookId));
        return book;
    }

    @Override
    public BookResponseDto deleteBook(Long bookId) {
        Book book = getBook(bookId);
        bookRepository.delete(book);
        return mapper.bookToBokResponseDto(book);
    }

    @Transactional
    @Override
    public BookResponseDto editBook(Long bookId, BookRequestDto bookRequestDto) {
        Book bookToEdit = getBook(bookId);
        bookToEdit.setName(bookRequestDto.getName());
        if (!bookRequestDto.getAuthorIds().isEmpty()) {
            List<Author> authors = new ArrayList<>();
            for (Long authorId: bookRequestDto.getAuthorIds()){
                Author author = authorService.getAuthor(authorId);
                authors.add(author);
            }
            bookToEdit.setAuthors(authors);
        }
        if(bookRequestDto.getCategoryId() != null){
            Category category = categoryService.getCategory(bookRequestDto.getCategoryId());
            bookToEdit.setCategory(category);
        }
        return mapper.bookToBokResponseDto(bookToEdit);
    }

    @Override
    public BookResponseDto addAuthorToBook(Long bookId, Long authorId) {
        Book book = getBook(bookId);
        Author author = authorService.getAuthor(authorId);
        //Verifie si cet est dèjas dans la liste des livres
        if(author.getBooks().contains(author)){
            throw new IllegalArgumentException("Cet auteur est déjà affecté à ce livre");
        }
        book.addAuthor(author);
        author.addBook(book);
        return mapper.bookToBokResponseDto(book);
    }

    @Override
    public BookResponseDto deteleAuthorFromBook(Long bookId, Long authorId) {
        Book book = getBook(bookId);
        Author author = authorService.getAuthor(authorId);
        //Si cet autor n'a pas de book
        if (!(author.getBooks().contains(book))) {
            throw new IllegalArgumentException("le livre n'a pas d'auteur");
        }
        author.removeBook(book);
        book.deleteAuthor(author);
        return mapper.bookToBokResponseDto(book);
    }

    @Override
    public BookResponseDto addCategoryToBook(Long bookId, Long categoryId) {
        Book book = getBook(bookId);
        Category category = categoryService.getCategory(categoryId);
        if(Objects.nonNull(book.getCategory())){
            throw new IllegalArgumentException("le livre a déjà une catégorie");
        }
        book.setCategory(category);
        category.addBook(book);
        return mapper.bookToBokResponseDto(book);
    }

    @Override
    public BookResponseDto removeCategoryFromBook(Long bookId, Long categoryId) {
        Book book = getBook(bookId);
        Category category = categoryService.getCategory(categoryId);
        if(!(Objects.nonNull(book.getCategory()))){
            throw new IllegalArgumentException("book does not have a category to delete(ce livre n'a pas de catégory à supprimer)");
        }
        book.setCategory(null);
        category.removeBook(book);
        return mapper.bookToBokResponseDto(book);
    }
}
