package com.readingisgood.data.model.statistic;

import lombok.Data;

@Data
public class StatisticRequest {
    private Long customerId;

    private int month;
}
