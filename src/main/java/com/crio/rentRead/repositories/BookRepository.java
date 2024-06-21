package com.crio.rentRead.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crio.rentRead.entityModels.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {
    
}
