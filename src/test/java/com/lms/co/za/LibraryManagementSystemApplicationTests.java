package com.lms.co.za;

import com.lms.co.za.exception.ResourceNotFoundException;
import com.lms.co.za.model.Book;
import com.lms.co.za.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class LibraryManagementSystemApplicationTests {

    @Autowired
    BookService bookService;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(bookService);
    }

    @Test
    public void getAllBooks() throws ResourceNotFoundException {
        List<Book> books = this.bookService.getAllBooks();
        Assertions.assertNotNull(books, "books should not be null");
        Assertions.assertFalse(books.isEmpty(), "books should not be empty");
    }

    @Test
    public void getBookById() throws ResourceNotFoundException {
        Long id = 1L;

        Book book = this.bookService.getBookById(id);
        Assertions.assertNotNull(book, "book should not be null");
        Assertions.assertEquals("0-670-81302-8", book.getIsbn());
        Assertions.assertEquals("It", book.getTitle());
        Assertions.assertEquals("Stephen King", book.getAuthor());
    }

    @Test
    public void getBookByISBN() throws ResourceNotFoundException {
        String isbn = "0-385-50420-9";

        Book book = this.bookService.getBookByISBN(isbn);
        Assertions.assertNotNull(book, "book should not be null");
        Assertions.assertEquals("Dan Brown", book.getAuthor());
    }

    @Test
    public void getByByAuthorContaining() throws ResourceNotFoundException {
        String searchCriteria = "Steph";
        List<Book> books = this.bookService.getBooksByAuthorContaining(searchCriteria);
        Assertions.assertNotNull(books, "books should not be null");
        Assertions.assertFalse(books.isEmpty(), "books should not be empty");
        Assertions.assertEquals(1, books.size(), "only book should exist for this test case for key search criteria: " + searchCriteria);

    }

    @Test
    public void addBook() throws DataIntegrityViolationException, ResourceNotFoundException {
        Book book = new Book();
        book.setTitle("Long Walk to Freedom");
        book.setAuthor("Nelsone Mandela");
        book.setPublisher("Little, Brown");
        book.setIsbn("0-316-87496-5");
        book.setQuantity(5);

        Book newBook = this.bookService.createBook(book);
        Assertions.assertNotNull(newBook, "book should not be null");
        Assertions.assertEquals(newBook.getId(), 4, "should equal 4 for this test case");

        List<Book> books = this.bookService.getAllBooks();
        Assertions.assertEquals(4, books.size(), "books size should equal 4");

    }

    @Test
    public void updateBook() throws ResourceNotFoundException {
        Long id = 1L;

        Book book = this.bookService.getBookById(id);
        Assertions.assertNotNull(book, "book should not be null");
        book.setQuantity(1);

        Book updatedBook = bookService.updateBook(id, book);
        Assertions.assertEquals(1, updatedBook.getQuantity());

    }





}
