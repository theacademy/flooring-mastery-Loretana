package com.flooringorder.service;

import com.flooringorder.dao.OrderDao;
import com.flooringorder.dao.ProductDao;
import com.flooringorder.dao.TaxDao;
import com.flooringorder.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class FlooringServiceImpl implements FlooringService {

    private TaxDao taxDao;
    private ProductDao productDao;
    private OrderDao orderDao;

    @Autowired
    public FlooringServiceImpl(TaxDao taxDao, ProductDao productDao, OrderDao orderDao) {
        this.taxDao = taxDao;
        this.productDao = productDao;
        this.orderDao = orderDao;
    }

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
