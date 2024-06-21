package com.crio.rentRead.repositoryServices;

import java.util.List;

import com.crio.rentRead.dto.Book;
import com.crio.rentRead.exceptions.BookNotFoundException;

public interface IBookRepositoryService {

    Book addBook(String title, String author, String genre, String availabilityStatus);

    Book saveBook(Book book);

    Book getBookById(int bookId) throws BookNotFoundException;

    Book updateBook(int bookId, String title, String author, String genre, String availabilityStatus)
            throws BookNotFoundException;

    List<Book> getAllBooks();

    void deleteBook(int bookId) throws BookNotFoundException;

}
