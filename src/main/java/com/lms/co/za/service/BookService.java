package com.lms.co.za.service;

import com.lms.co.za.exception.ResourceNotFoundException;
import com.lms.co.za.model.Book;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface BookService {

    Book getBookById(Long id) throws ResourceNotFoundException;
    Book getBookByISBN(String isbn) throws ResourceNotFoundException;
    List<Book> getAllBooks() throws ResourceNotFoundException;
    List<Book> getBooksByAuthorContaining(String author) throws ResourceNotFoundException;
    Book createBook(Book book) throws DataIntegrityViolationException;

    Book updateBook(Long id, Book book) throws ResourceNotFoundException;
    void deleteBookById(Long id) throws ResourceNotFoundException;



}
