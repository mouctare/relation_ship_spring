package com.datajpa.relationship.service;

import com.datajpa.relationship.dto.requestDto.AuthorRequestDto;
import com.datajpa.relationship.dto.requestDto.responseDto.AuthorResponseDto;
import com.datajpa.relationship.model.Author;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface AuthorService {
    AuthorResponseDto addAuthor(AuthorRequestDto authorRequestDto);
    List<AuthorResponseDto> getAuthors();
    AuthorResponseDto getAuthorById(Long authorId);
    Author getAuthor(Long authorId);
    AuthorResponseDto deleteAuthor(Long authorId);
    AuthorResponseDto editAuthor(Long authorId, AuthorRequestDto authorRequestDto);
    AuthorResponseDto addZipcodeToAuthor(Long authorId, Long zipcodeId);
    AuthorResponseDto addZipcodeFromAuthor(Long authorId);
    AuthorResponseDto deleteZipcodeFromAuthor(Long authorId);
}
