package com.example.datacollector.tasker;

import com.example.datacollector.restapi.SaleOrderController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.LinkedList;
import java.util.List;

public class Tasker {

    @Autowired
    private SaleOrderController controller;

    private List<String> CACHE = new LinkedList<>();

    @Scheduled(cron = "0/30 * * * * ? ")
    public void scheduledTask() {
    }
}
