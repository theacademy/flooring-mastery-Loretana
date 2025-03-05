package com.flooringorder.service;

import com.flooringorder.dao.DataPersistanceException;
import com.flooringorder.dao.OrderDao;
import com.flooringorder.dao.ProductDao;
import com.flooringorder.dao.TaxDao;
import com.flooringorder.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Component
public class FlooringServiceImpl implements FlooringService {

    private TaxDao taxDao;
    private ProductDao productDao;
    private OrderDao orderDao;
    private final static DateTimeFormatter formatterISO = DateTimeFormatter.ofPattern("yyyy-MM-dd");


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

    /*
     *  Retrieve a list of Order object based on a date
     *  Return null if nothing found
     * */
    @Override
    public List<Order> getOrdersByDate(String dateAsText) throws OrderNotFoundException, DataPersistanceException {
        try {
            LocalDate userDate = LocalDate.parse(dateAsText, formatterISO);
            List<Order> ordersFound = orderDao.getAllOrderByDate(userDate);
            if(ordersFound == null) {
                throw new OrderNotFoundException("ERROR: No orders found on this date.");
            }
            return ordersFound;
        } catch (DateTimeParseException e) {
            throw new DataPersistanceException("ERROR: Invalid date format, must follow ISO-8601 YYYY-MM-DD");
        }
    }

}
