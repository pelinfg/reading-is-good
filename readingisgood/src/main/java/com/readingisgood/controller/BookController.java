package com.readingisgood.controller;

import com.readingisgood.data.entity.book.Book;
import com.readingisgood.data.entity.book.BookStock;
import com.readingisgood.data.model.book.BookStockModel;
import com.readingisgood.service.book.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @PostMapping("/addNewBook")
    public ResponseEntity<String> addNewBook(@RequestBody Book book) {
        try {
            service.createNewBook(book);
            return new ResponseEntity<>("Book added successfully!", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Could not save new book: {}", e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateStock")
    public ResponseEntity<BookStockModel> updateStock(@RequestBody BookStockModel request) {
        try {
            BookStock stock = service.getBookStockByBookId(request.getBookId());
            BookStockModel res = service.updateBookStock(stock.getId(), request.getNewStock());
            log.info("Book stock updated successfully!");
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Could not update book stock: {}", e.toString());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
