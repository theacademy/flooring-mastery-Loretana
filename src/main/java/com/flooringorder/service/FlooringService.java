package com.flooringorder.service;

import com.flooringorder.dao.DataPersistanceException;
import com.flooringorder.model.Order;

import java.time.LocalDate;
import java.util.List;

public interface FlooringService {

    Order addOrder(Order newOrder, LocalDate date);

    Order editOrder(LocalDate date, Order order);

    boolean validateOrderInfo(Order order);

    Order removeOrder(int orderId, LocalDate date);

    Order getOrder(int orderId, LocalDate date);

    List<Order> getOrdersByDate(String dateAsText) throws DataPersistanceException, OrderNotFoundException;

}
