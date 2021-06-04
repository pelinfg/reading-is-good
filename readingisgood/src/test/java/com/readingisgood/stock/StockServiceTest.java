package com.readingisgood.stock;

import com.readingisgood.AbstractTestBase;
import com.readingisgood.data.entity.book.Book;
import com.readingisgood.data.entity.book.BookRepository;
import com.readingisgood.data.entity.book.BookStock;
import com.readingisgood.data.entity.book.BookStockRepository;
import com.readingisgood.service.book.BookService;
import com.readingisgood.service.stock.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class StockServiceTest extends AbstractTestBase {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookStockRepository bookStockRepository;

    private BookService bookService;

    private StockService stockService;

    @BeforeEach
    public void setUp() {
        bookService = new BookService(bookRepository, bookStockRepository);
        stockService = new StockService(bookService);
        Book firstBook = new Book();
        firstBook.setCreatedAt(new Date());
        firstBook.setIsbn("132456");
        firstBook.setTitle("title");
        firstBook.setPrice(5d);
        BookStock bookStock = new BookStock();
        bookStock.setStock(10);
        firstBook.setBookStock(bookStock);
        bookService.createNewBook(firstBook);

        Book secondBook = new Book();
        secondBook.setCreatedAt(new Date());
        secondBook.setIsbn("23456");
        secondBook.setTitle("title2");
        secondBook.setPrice(20d);
        BookStock bookStock2 = new BookStock();
        bookStock2.setStock(10);
        secondBook.setBookStock(bookStock2);
        bookService.createNewBook(secondBook);
    }

    @Test
    public void testConcurrentBookStockUpdate() throws InterruptedException {
        List<BookStock> bookStocks = bookStockRepository.findAll();
        Long bookStockId = bookStocks.get(0).getId();
        final ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(() -> bookService.updateBookStock(bookStockId, 1));

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        final BookStock item = bookStockRepository.findById(bookStockId).get();
        assertEquals(1, item.getStock());
    }

    @Test
    public void testUpdateBookStock() {
        List<BookStock> bookStocks = bookStockRepository.findAll();
        Long bookStockId = bookStocks.get(0).getId();
        bookStockRepository.updateStock(bookStockId, 500);
        final BookStock item = bookStockRepository.findById(bookStockId).get();
        assertEquals(500, item.getStock());
    }

    @Test
    @DisplayName("Injected components are not null.")
    public void injectedComponentsAreNotNull() {
        assertThat(bookRepository).isNotNull();
        assertThat(bookStockRepository).isNotNull();
        assertThat(bookService).isNotNull();
        assertThat(stockService).isNotNull();
    }

}
