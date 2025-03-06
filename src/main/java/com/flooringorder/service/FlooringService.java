package com.flooringorder.service;

import com.flooringorder.dao.DataPersistanceException;
import com.flooringorder.model.Order;
import com.flooringorder.model.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface FlooringService {

    Order addOrder(Order newOrder, LocalDate date) throws InvalidTaxInformationException, DataPersistanceException, InvalidOrderInformationException;

    Order editOrder(LocalDate date, Order order) throws DataPersistanceException;

    boolean validateOrderInfo(Order order) throws InvalidOrderInformationException, DataPersistanceException, InvalidTaxInformationException;

    Order removeOrder(int orderId, LocalDate date);

    Order getOrder(int orderId, LocalDate date) throws DataPersistanceException, OrderNotFoundException;

    List<Order> getOrdersByDate(LocalDate date) throws DataPersistanceException, OrderNotFoundException;

    List<Product> getAllProduct() throws DataPersistanceException;

    int getLatestOrderId() throws DataPersistanceException;

    BigDecimal calculateOrderCost(Order order);


}
