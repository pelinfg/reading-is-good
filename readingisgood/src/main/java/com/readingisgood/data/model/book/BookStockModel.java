package com.readingisgood.data.model.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
public class BookStockModel {
    private Long bookId;

    private int newStock;

    @Nullable
    private int oldStock;
}
