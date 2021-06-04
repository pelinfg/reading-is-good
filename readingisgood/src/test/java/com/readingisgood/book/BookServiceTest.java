package com.readingisgood.book;

import com.readingisgood.AbstractTestBase;
import com.readingisgood.data.entity.book.Book;
import com.readingisgood.data.entity.book.BookRepository;
import com.readingisgood.data.entity.book.BookStock;
import com.readingisgood.data.entity.book.BookStockRepository;
import com.readingisgood.service.book.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import java.util.Date;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class BookServiceTest extends AbstractTestBase {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    BookStockRepository bookStockRepository;

    private BookService bookService;

    @BeforeEach
    public void setUp() {
        bookService = new BookService(bookRepository, bookStockRepository);
        Book b = new Book();
        b.setCreatedAt(new Date());
        b.setIsbn("132456");
        b.setTitle("title");
        b.setUpdatedAt(new Date());
        b.setPrice(5.75);
        BookStock bookStock = new BookStock();
        bookStock.setStock(3);
        b.setBookStock(bookStock);
        bookService.createNewBook(b);
    }

    @Test
    public void testBookTitleIsNull() {
        Book book = new Book();
        book.setIsbn("1354");
        book.setPrice(10d);
        book.setTitle("");
        Set<ConstraintViolation<Book>> constraintViolations =
                validator.validate(book);
        assertEquals( 1, constraintViolations.size());
    }

    @Test
    @DisplayName("Injected components are not null.")
    public void injectedComponentsAreNotNull() {
        assertThat(bookRepository).isNotNull();
        assertThat(bookService).isNotNull();
    }

}
