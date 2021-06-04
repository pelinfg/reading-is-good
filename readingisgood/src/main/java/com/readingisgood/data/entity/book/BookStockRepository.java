package com.readingisgood.data.entity.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface BookStockRepository extends JpaRepository<BookStock, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update BookStock bookStock set bookStock.stock = :newStock where bookStock.id = :id")
    void updateStock(@Param("id") Long id, @Param("newStock") int newStock);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<BookStock> findById(Long id);

    @Query("select bookStock from BookStock bookStock where bookStock.book.id = :bookId")
    Optional<BookStock> findStockByBookId(@Param("bookId") Long bookId);

}
