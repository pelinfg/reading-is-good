package com.readingisgood.data.entity.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select book from Book book where book.id = :id")
    Optional<Book> getBookForUpdate(@Param("id") Long id);
}
