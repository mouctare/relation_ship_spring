package com.datajpa.relationship.service;

import com.datajpa.relationship.dto.requestDto.AuthorRequestDto;
import com.datajpa.relationship.dto.requestDto.mapper;
import com.datajpa.relationship.dto.requestDto.responseDto.AuthorResponseDto;
import com.datajpa.relationship.model.Author;
import com.datajpa.relationship.model.ZipCode;
import com.datajpa.relationship.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorRepository authorRepository;
    private final ZipcodeService zipcodeService;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, ZipcodeService zipcodeService) {
        this.authorRepository = authorRepository;
        this.zipcodeService = zipcodeService;
    }

    @Transactional
    @Override
    public AuthorResponseDto addAuthor(AuthorRequestDto authorRequestDto) {
        Author author = new Author();
        author.setName(authorRequestDto.getName());
        if(authorRequestDto.getZipcodeId() == null){
            throw new IllegalArgumentException("l'auteur a besoin d'un code postal");
        }
        ZipCode zipCode = zipcodeService.getZipcode(authorRequestDto.getZipcodeId());
        author.setZipCode(zipCode);
        authorRepository.save(author);
        return mapper.authorToAuthorResponseDto(author);
    }

    @Override
    public List<AuthorResponseDto> getAuthors() {
        List<Author> authors = StreamSupport
                .stream(authorRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return mapper.authorsToAuthorResponseDtos(authors);
    }

    @Override
    public AuthorResponseDto getAuthorById(Long authorId) {
        return mapper.authorToAuthorResponseDto(getAuthor(authorId));
    }

    @Override
    // Cette méthode va me servire dans les autres opérations
    public Author getAuthor(Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() ->
                new IllegalArgumentException("author with id:" + authorId + " could not be found"));
        return author;
    }

    @Override
    public AuthorResponseDto deleteAuthor(Long authorId) {
        Author author = getAuthor(authorId);
        authorRepository.delete(author);
        return mapper.authorToAuthorResponseDto(author);
    }

    @Transactional
    @Override
    public AuthorResponseDto editAuthor(Long authorId, AuthorRequestDto authorRequestDto) {
        Author authorToEdit = getAuthor(authorId);
        authorToEdit.setName(authorRequestDto.getName());
        if(authorRequestDto.getZipcodeId() != null){
            ZipCode zipCode = zipcodeService.getZipcode(authorRequestDto.getZipcodeId());
            authorToEdit.setZipCode(zipCode);
        }
        return mapper.authorToAuthorResponseDto(authorToEdit);
    }

    @Transactional
    @Override
    public AuthorResponseDto addZipcodeToAuthor(Long authorId, Long zipcodeId) {
        Author author = getAuthor(authorId);
        ZipCode zipCode = zipcodeService.getZipcode(zipcodeId);
        if(Objects.nonNull(author.getZipCode())){
            throw new RuntimeException("author already has a zipcode");
        }
        author.setZipCode(zipCode);
        return mapper.authorToAuthorResponseDto(author);
    }

    @Transactional
    @Override
    public AuthorResponseDto addZipcodeFromAuthor(Long authorId) {
        Author author = getAuthor(authorId);
        author.setZipCode(null);
        return mapper.authorToAuthorResponseDto(author);
    }

    @Transactional
    @Override
    public AuthorResponseDto deleteZipcodeFromAuthor(Long authorId) {
        Author author = getAuthor(authorId);
        author.setZipCode(null);
        return mapper.authorToAuthorResponseDto(author);
    }

}
