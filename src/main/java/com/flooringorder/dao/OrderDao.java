package com.flooringorder.dao;

import com.flooringorder.model.Order;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

public interface OrderDao {


    Order addOrder(Order newOrder, LocalDate date);

    List<Order> getAllOrderByDate(LocalDate date);

    Order removeOrder(int orderId, LocalDate date);

    Order updateOrder(int orderId, LocalDate date);

    Order getOrderByIdAndDate(int orderId, LocalDate date);

    void exportAll();

}
