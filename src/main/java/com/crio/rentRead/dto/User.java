package com.crio.rentRead.dto;

import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {

    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String role;

    private Set<Book> rentedBooks;

}
