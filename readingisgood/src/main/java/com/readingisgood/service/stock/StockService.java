package com.readingisgood.service.stock;

import com.readingisgood.data.entity.book.BookStock;
import com.readingisgood.data.model.book.BookStockModel;
import com.readingisgood.service.book.BookService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Service
@AllArgsConstructor
public class StockService {
    private final BookService bookService;

    public List<BookStockModel> checkStockAndDecreaseStock(Map<Long, Integer> bookQuantityMap) throws NoSuchMethodException {
        int amount;
        List<BookStock> bookStocks = new ArrayList<>();
        for (Long bookId : bookQuantityMap.keySet()) {
            amount = bookQuantityMap.get(bookId);
            BookStock bookStock = bookService.getBookStockByBookId(bookId);
            int stock = bookStock.getStock();
            if (stock == 0 || stock < amount) {
                throw new NoSuchMethodException("Book stock is not available.");
            }
            bookStocks.add(bookStock);
        }

        List<BookStockModel> result = bookStocks.stream().map(i ->
            bookService.updateBookStock(i.getId(), i.getStock() - bookQuantityMap.get(i.getBook().getId()))
        ).collect(Collectors.toList());

        return result;
    }

}
