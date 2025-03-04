package com.flooringorder.service;

import com.flooringorder.model.Order;

import java.time.LocalDate;

public interface FlooringService {

    Order addOrder(Order newOrder, LocalDate date);

    Order editOrder(LocalDate date, Order order);

    boolean validateOrderInfo(Order order);

    Order removeOrder(int orderId, LocalDate date);

    Order getOrder(int orderId, LocalDate date);

}
