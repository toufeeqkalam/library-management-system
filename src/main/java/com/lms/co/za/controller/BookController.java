package com.lms.co.za.controller;

import com.lms.co.za.exception.DuplicateResourceException;
import com.lms.co.za.exception.ResourceNotFoundException;
import com.lms.co.za.exception.model.ApiError;
import com.lms.co.za.model.Book;
import com.lms.co.za.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@Validated
@RequestMapping(value = "v1")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    BookService bookService;

    @Operation(summary = "Get book by given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found for id", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "No book found for given id", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    @GetMapping(value = "/book/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> getBookById(@PathVariable(value = "id") @NotNull Long id) throws ResourceNotFoundException {
        Book book = this.bookService.getBookById(id);
        return ResponseEntity.ok().body(book);
    }


    @Operation(summary = "Get book by author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found for author", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "No book found for given author", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    @GetMapping(value = "/book/author/{author}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getBookByAuthor(@PathVariable(value = "author") @NotBlank @Size(min = 3) String author) throws ResourceNotFoundException {
        List<Book> books = this.bookService.getBooksByAuthorContaining(author);
        return ResponseEntity.ok().body(books);
    }

    @Operation(summary = "Get book by ISBN reference")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found for ISBN reference", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "No book found for given ISBN reference", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    @GetMapping(value = "/book/isbn/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> getBookByISBN(@PathVariable(value = "isbn") @NotBlank String isbn) throws ResourceNotFoundException {
        Book book = this.bookService.getBookByISBN(isbn);
        return ResponseEntity.ok().body(book);
    }

    @Operation(summary = "Get all books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return all books", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "No books found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    @GetMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getAllBooks() throws ResourceNotFoundException {
        List<Book> books = this.bookService.getAllBooks();
        return ResponseEntity.ok().body(books);
    }

    @Operation(summary = "Create a new instance of a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book successfully created, returns uri for new book with id param", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "No books found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    @PostMapping(value = "/book", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBook(@RequestBody @Valid @NotNull Book book, BindingResult bindingResult) throws DuplicateResourceException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        } else {
            Book newBook = this.bookService.createBook(book);
            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newBook.getId()).toUri()).build();
        }
    }

    @Operation(summary = "Update book with given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated book successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "No book found for given id", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    @PutMapping(value = "/book/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateBook(@PathVariable(value = "id") Long id, @RequestBody Book book) throws ResourceNotFoundException {
        Book updatedBook = this.bookService.updateBook(id, book);
        return ResponseEntity.ok().body(updatedBook);
    }

    @Operation(summary = "Delete book with given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found for given id", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "No book found for given id", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    @DeleteMapping(value = "/book/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteBookById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        this.bookService.deleteBookById(id);
        return ResponseEntity.ok().build();
    }
}
