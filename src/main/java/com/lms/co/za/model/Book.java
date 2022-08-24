package com.lms.co.za.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema
@Entity(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique book identifier", hidden = true)
    private Long id;
    @NotBlank
    @Schema(description = "Title of book", example = "It")
    @Column(name = "title", nullable = false)
    private String title;
    @NotBlank
    @Schema(description = "Author of book", example = "Stephen King")
    @Column(name = "author", nullable = false)
    private String author;
    @NotBlank
    @Schema(description = "Publisher of book", example = "Viking")
    @Column(name = "publisher", nullable = false)
    private String publisher;
    @NotBlank
    @Schema(description = "ISBN reference of book", example = "0-670-81302-8")
    @Column(name = "isbn", nullable = false)
    private String isbn;
    @NotNull
    @Schema(description = "Number of books in stock for a given book title", example = "2")
    @Column(name = "quantity", nullable = false)
    private int quantity;

}
