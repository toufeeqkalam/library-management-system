package com.lms.co.za.service.impl;

import com.lms.co.za.exception.ResourceNotFoundException;
import com.lms.co.za.model.Book;
import com.lms.co.za.repository.BookRepository;
import com.lms.co.za.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    BookRepository bookRepository;

    @Override
    public Book getBookById(Long id) throws ResourceNotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found for id: " + id));
    }

    @Override
    public Book getBookByISBN(String isbn) throws ResourceNotFoundException {
        return this.bookRepository.findBookByIsbn(isbn).orElseThrow(() -> new ResourceNotFoundException("Book not found for ISBN reference: " + isbn));
    }

    @Override
    public List<Book> getAllBooks() throws ResourceNotFoundException {
        List<Book> books =  this.bookRepository.findAll();
        if(books.isEmpty()){
            throw new ResourceNotFoundException("No books found in datasource");
        }else {
            return books;
        }
    }

    @Override
    public List<Book> getBooksByAuthorContaining(String author) throws ResourceNotFoundException {
        List<Book> books =  this.bookRepository.findBookByAuthorContainingIgnoreCase(author);
        if(books.isEmpty()){
            throw new ResourceNotFoundException("No books found for author: " + author);
        }else {
            return books;
        }
    }

    @Override
    public Book createBook(Book book) throws DataIntegrityViolationException {
        //added unique constraint to ISBN column on the books schema, catch dataIntegrationViolationException and return via global exception handler.
        return this.bookRepository.saveAndFlush(book);
    }

    @Override
    public Book updateBook(Long id, Book book) throws ResourceNotFoundException {
        Optional<Book> existingBook = this.bookRepository.findById(id);
        if(existingBook.isPresent()){
            //!=/!"".equalIgnoreCase(val) checks not necessary as bean validation is handled at class level.
            Book updatedBook = existingBook.get();
            updatedBook.setTitle(book.getTitle());
            updatedBook.setAuthor(book.getAuthor());
            updatedBook.setPublisher(book.getPublisher());
            updatedBook.setIsbn(book.getIsbn());
            updatedBook.setQuantity(book.getQuantity());
            return this.bookRepository.saveAndFlush(updatedBook);
        }else {
            throw new ResourceNotFoundException("No book found for id: " + id);
        }
    }

    @Override
    public void deleteBookById(Long id) throws ResourceNotFoundException {
        boolean bookExists = this.bookRepository.existsById(id);
        if(!bookExists){
            throw new ResourceNotFoundException("no book found for id: " + id);
        }else {
            this.bookRepository.deleteById(id);
        }
    }
}
