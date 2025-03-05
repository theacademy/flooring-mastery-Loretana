package com.flooringorder.dao;

import com.flooringorder.model.Order;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

public interface OrderDao {


    Order addOrder(Order newOrder, LocalDate date) throws DataPersistanceException;

    List<Order> getAllOrderByDate(LocalDate date) throws DataPersistanceException;

    Order removeOrder(int orderId, LocalDate date);

    Order updateOrder(int orderId, LocalDate date);

    Order getOrderByIdAndDate(int orderId, LocalDate date) throws DataPersistanceException;

    void exportAll() throws DataPersistanceException;

}
