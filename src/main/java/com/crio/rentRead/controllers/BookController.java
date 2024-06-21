package com.crio.rentRead.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crio.rentRead.dto.Book;
import com.crio.rentRead.exceptions.BookNotAvailableException;
import com.crio.rentRead.exceptions.BookNotFoundException;
import com.crio.rentRead.exceptions.BookNotRentedException;
import com.crio.rentRead.exceptions.RentalException;
import com.crio.rentRead.exceptions.UserNotFoundException;
import com.crio.rentRead.exchanges.AddBookRequest;
import com.crio.rentRead.exchanges.GetAllBooksResponse;
import com.crio.rentRead.exchanges.RentBookResponse;
import com.crio.rentRead.exchanges.ReturnBookResponse;
import com.crio.rentRead.exchanges.UpdateBookRequest;
import com.crio.rentRead.services.IBookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(BookController.BOOK_API_ENDPOINT)
public class BookController {
    public static final String BOOK_API_ENDPOINT = "/books";

    @Autowired
    private IBookService bookService;

    // Endpoint to create a new book, accessible only by ADMIN

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> addBook(@Valid @RequestBody AddBookRequest addBookRequest) {
        Book book = bookService.addBook(addBookRequest);
        return ResponseEntity.ok().body(book);
    }

    // Endpoint to rent a book, accessible only by USER

    @PostMapping("/{bookId}/rent")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RentBookResponse> rentBook(@PathVariable(value = "bookId") int bookId,
            @AuthenticationPrincipal UserDetails userDetails)
            throws UserNotFoundException, BookNotFoundException, BookNotAvailableException, RentalException {
        RentBookResponse rentBookResponse = bookService.rentBook(bookId, userDetails);
        return ResponseEntity.ok().body(rentBookResponse);
    }

    // Endpoint to return a rented book, accessible only by USER

    @PostMapping("/{bookId}/return")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReturnBookResponse> returnBook(@PathVariable(value = "bookId") int bookId,
            @AuthenticationPrincipal UserDetails userDetails)
            throws UserNotFoundException, BookNotFoundException, BookNotRentedException {
        ReturnBookResponse returnBookResponse = bookService.returnBook(bookId, userDetails);
        return ResponseEntity.ok().body(returnBookResponse);
    }

    // Endpoint to get all books, accessible by both USER and ADMIN

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<GetAllBooksResponse> getAllBooks() {
        GetAllBooksResponse getAllBooksResponse = bookService.findAllBooks();
        return ResponseEntity.ok().body(getAllBooksResponse);
    }

    // Endpoint to update book details, accessible only by ADMIN

    @PutMapping("/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> updateBook(@PathVariable(value = "bookId") int bookId,
            @Valid @RequestBody UpdateBookRequest updateBookRequest) throws BookNotFoundException {
        Book book = bookService.updateBook(bookId, updateBookRequest);
        return ResponseEntity.ok().body(book);
    }

    // Endpoint to delete a book, accessible only by ADMIN

    @DeleteMapping("/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteBook(@PathVariable(value = "bookId") int bookId) throws BookNotFoundException {
        String response = bookService.deleteBook(bookId);
        return ResponseEntity.ok().body(response);
    }

}
