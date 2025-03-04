package com.flooringorder.service;

import com.flooringorder.model.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class FlooringServiceImpl implements FlooringService {


    @Override
    public Order addOrder(Order newOrder, LocalDate date) {
        return null;
    }

    @Override
    public Order editOrder(LocalDate date, Order order) {
        return null;
    }

    @Override
    public boolean validateOrderInfo(Order order) {
        return false;
    }

    @Override
    public Order removeOrder(int orderId, LocalDate date) {
        return null;
    }

    @Override
    public Order getOrder(int orderId, LocalDate date) {
        return null;
    }

}
