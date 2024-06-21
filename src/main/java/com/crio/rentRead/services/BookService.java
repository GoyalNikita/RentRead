package com.crio.rentRead.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.crio.rentRead.dto.Book;
import com.crio.rentRead.dto.User;
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
import com.crio.rentRead.repositoryServices.IBookRepositoryService;
import com.crio.rentRead.repositoryServices.IUserRepositoryService;

@Service
public class BookService implements IBookService {

    @Autowired
    private IUserRepositoryService userRepositoryService;

    @Autowired
    private IBookRepositoryService bookRepositoryService;

    @Override
    public Book addBook(AddBookRequest addBookRequest) {
        String title = addBookRequest.getTitle();
        String author = addBookRequest.getAuthor();
        String genre = addBookRequest.getGenre();
        String availabilityStatus = addBookRequest.getAvailabilityStatus().toUpperCase();

        Book book = bookRepositoryService.addBook(title, author, genre, availabilityStatus);
        return book;
    }

    @Override
    public RentBookResponse rentBook(int bookId, UserDetails userDetails)
            throws UserNotFoundException, BookNotFoundException, BookNotAvailableException, RentalException {
        String email = userDetails.getUsername();

        User user = userRepositoryService.getUserByEmail(email);
        Book book = bookRepositoryService.getBookById(bookId);

        checkRentingEligibility(user, book);

        Set<Book> rentedBooks = user.getRentedBooks();
        rentedBooks.add(book);

        book.setAvailabilityStatus("Not Available");

        bookRepositoryService.saveBook(book);
        user = userRepositoryService.saveUser(user);
        RentBookResponse rentBookResponse = new RentBookResponse(user.getId(), user.getFirstName(), user.getLastName(),
                user.getRole(), user.getRentedBooks());
        return rentBookResponse;
    }

    @Override
    public ReturnBookResponse returnBook(int bookId, UserDetails userDetails)
            throws UserNotFoundException, BookNotFoundException, BookNotRentedException {
        String email = userDetails.getUsername();

        User user = userRepositoryService.getUserByEmail(email);
        Book book = bookRepositoryService.getBookById(bookId);

        if (hasRentedBook(user, book)) {
            Set<Book> rentedBooks = user.getRentedBooks();
            rentedBooks.remove(book);
            book.setAvailabilityStatus("Available");
            user = userRepositoryService.saveUser(user);
            bookRepositoryService.saveBook(book);
            ReturnBookResponse returnBookResponse = new ReturnBookResponse(user.getId(), user.getFirstName(),
                    user.getLastName(), user.getRole(), user.getRentedBooks());
            return returnBookResponse;
        } else
            throw new BookNotRentedException("This book is not rented");
    }

    @Override
    public Book updateBook(int bookId, UpdateBookRequest updateBookRequest) throws BookNotFoundException {
        String title = updateBookRequest.getTitle();
        String author = updateBookRequest.getAuthor();
        String genre = updateBookRequest.getGenre();
        String availabilityStatus = updateBookRequest.getAvailabilityStatus().toUpperCase();
        Book book = bookRepositoryService.updateBook(bookId, title, author, genre, availabilityStatus);
        return book;
    }

    @Override
    public GetAllBooksResponse findAllBooks() {
        List<Book> books = bookRepositoryService.getAllBooks();
        GetAllBooksResponse getAllBooksResponse = new GetAllBooksResponse(books);
        return getAllBooksResponse;
    }

    @Override
    public String deleteBook(int bookId) throws BookNotFoundException {
        String response = "Deleted Successfully";
        bookRepositoryService.deleteBook(bookId);
        return response;
    }

    private void checkRentingEligibility(User user, Book book) throws BookNotAvailableException, RentalException {
        String availabilityStatus = book.getAvailabilityStatus();
        Set<Book> rentedBooks = user.getRentedBooks();

        if (availabilityStatus.equals("Not Available"))
            throw new BookNotAvailableException("This Book is not available for rent");

        if (rentedBooks.size() >= 2)
            throw new RentalException("User already has two active rentals");
    }

    private boolean hasRentedBook(User user, Book book) {
        Set<Book> rentedBooks = user.getRentedBooks();
        return rentedBooks.contains(book);
    }

}
