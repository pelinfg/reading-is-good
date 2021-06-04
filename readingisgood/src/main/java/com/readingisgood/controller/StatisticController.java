package com.readingisgood.controller;

import com.readingisgood.data.model.statistic.StatisticRequest;
import com.readingisgood.data.model.statistic.StatisticResponse;
import com.readingisgood.service.statistic.StatisticService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/statistic")
public class StatisticController {

    @Autowired
    private StatisticService service;

    @GetMapping("/getMonthly")
    public ResponseEntity<StatisticResponse> getById(@RequestBody StatisticRequest request) {
        try {
            StatisticResponse res = service.getStatisticMonthly(request.getCustomerId(), request.getMonth());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Could not get statistic: {}", e.toString());
            return ResponseEntity.notFound().build();
        }
    }
}
