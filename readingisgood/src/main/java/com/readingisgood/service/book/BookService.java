package com.readingisgood.service.book;

import com.readingisgood.data.entity.book.Book;
import com.readingisgood.data.entity.book.BookRepository;
import com.readingisgood.data.entity.book.BookStock;
import com.readingisgood.data.entity.book.BookStockRepository;
import com.readingisgood.data.model.book.BookStockModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookStockRepository bookStockRepository;

    public void createNewBook(Book book) {
        log.info("Saving book : {}", book.toString());
        Optional<BookStock> bookStock = Optional.ofNullable(book.getBookStock());
        book.setBookStock(null);
        book = bookRepository.saveAndFlush(book);

        bookStock = bookStock.isEmpty() ? Optional.of(new BookStock()) : bookStock;
        bookStock.get().setBook(book);
        bookStockRepository.save(bookStock.get());

    }

    @Transactional
    public BookStockModel updateBookStock(Long id, Integer newStock) {
        BookStock bookStock = getBookStockForUpdate(id);
        final int oldStock = bookStock.getStock();
        bookStockRepository.updateStock(bookStock.getId(), newStock);
        return new BookStockModel(bookStock.getBook().getId(), newStock, oldStock);
    }

    @Transactional
    public BookStock getBookStockForUpdate(Long id) {
        Optional<BookStock> bookStock = bookStockRepository.findById(id);
        return bookStock.orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public BookStock getBookStockByBookId(Long bookId) {
        Optional<BookStock> bookStock = bookStockRepository.findStockByBookId(bookId);
        return bookStock.orElseThrow(NoSuchElementException::new);
    }

    public Long getBookIdByStockId(Long stockId) {
        Long bookId = bookStockRepository.findById(stockId).get().getBook().getId();
        return bookId;
    }

}
