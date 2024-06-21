package com.crio.rentRead.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Book {
    
    private int id;

    private String title;

    private String author;

    private String genre;

    private String availabilityStatus;
}
