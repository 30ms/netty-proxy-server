package com.example.datacollector.tasker;

import com.example.datacollector.restapi.RestRequest;
import com.example.datacollector.restapi.SaleOrderController;
import com.example.datacollector.rpc.sale.list.SaleOrderListQueryResponse;
import com.example.datacollector.rpc.sale.list.SaleOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Tasker {

    @Autowired
    private SaleOrderController controller;

    private List<String> CACHE = new LinkedList<>();

    @Scheduled(cron = "0/30 * * * * ? ")
    public void scheduledTask() {
        System.out.println("执行刷新销售单任务任务：" + LocalDateTime.now());
        try {
            ResponseEntity<SaleOrderListQueryResponse> responseResponseEntity = controller.saleOrderList(LocalDate.now(), LocalDate.now(),"正常零售", 50, 1);

            SaleOrderListQueryResponse response = responseResponseEntity.getBody();
            List<SaleOrder> saleOrderStream = Arrays.stream(response.getSaleOrders())
                    .filter(saleOrder -> StringUtils.hasLength(saleOrder.getNote())&&(saleOrder.getNote().length() == 11)).collect(Collectors.toList());

            for (SaleOrder saleOrder : saleOrderStream) {
                if (CACHE.contains(saleOrder.getOrderId())) {
                    break;
                }
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.postForEntity("http://localhost:9402/rest/erp/SynchronizeSalesOrder",
                        new RestRequest(saleOrder.getOrderId(), saleOrder.getNote(), saleOrder.getActualTotalSellingPrice()), void.class);
                if (responseResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    CACHE.add(saleOrder.getOrderId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
