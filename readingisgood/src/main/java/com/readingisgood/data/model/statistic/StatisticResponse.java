package com.readingisgood.data.model.statistic;

import lombok.Data;

@Data
public class StatisticResponse {
    private String month;

    private int orderCount;

    private int bookCount;

    private double amount;
}
