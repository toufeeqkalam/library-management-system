package com.lms.co.za.service;

import com.lms.co.za.exception.DuplicateResourceException;
import com.lms.co.za.exception.ResourceNotFoundException;
import com.lms.co.za.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book getBookById(Long id) throws ResourceNotFoundException;
    Book getBookByISBN(String isbn) throws ResourceNotFoundException;
    List<Book> getAllBooks() throws ResourceNotFoundException;
    List<Book> getBooksByAuthorContaining(String author) throws ResourceNotFoundException;
    Book createBook(Book book) throws DuplicateResourceException;

    Book updateBook(Long id, Book book) throws ResourceNotFoundException;
    void deleteBookById(Long id) throws ResourceNotFoundException;



}
