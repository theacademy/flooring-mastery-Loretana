package com.flooringorder.dao;

import com.flooringorder.model.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoStubFileImpl implements OrderDao {

    public Order onlyOrder;
    private static final DateTimeFormatter FORMATTER_ISO = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public OrderDaoStubFileImpl() {
        int orderId = 1;
        LocalDate orderDate = LocalDate.parse("2030-01-01", FORMATTER_ISO);
        onlyOrder = new Order(orderDate, orderId);
        onlyOrder.setCustomerName("Ada Lovelace");
        onlyOrder.setState("Texas");
        onlyOrder.setArea(new BigDecimal("249.00"));
        onlyOrder.setProductType("Tile");
        onlyOrder.setTaxRate(new BigDecimal("4.45"));
        onlyOrder.setCostPerSquareFoot(new BigDecimal("3.50"));
        onlyOrder.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        onlyOrder.setMaterialCost(new BigDecimal("871.50"));
        onlyOrder.setLaborCost(new BigDecimal("1033.35"));
        onlyOrder.setTax(new BigDecimal("84.77"));
        onlyOrder.setTotal(new BigDecimal("1989.62"));
    }

    @Override
    public Order addOrder(Order newOrder, LocalDate date) throws DataPersistanceException {
        if(newOrder.getOrderId() == onlyOrder.getOrderId()) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public List<Order> getAllOrderByDate(LocalDate date) throws DataPersistanceException {
        List<Order> ordersList = new ArrayList<>();
        if(date.equals(onlyOrder.getDate())) {
            ordersList.add(onlyOrder);
        }
        return ordersList;
    }

    @Override
    public Order removeOrder(int orderId, LocalDate date) throws DataPersistanceException {
        if (orderId == onlyOrder.getOrderId()) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public Order updateOrder(Order orderEdited, LocalDate date) throws DataPersistanceException {
        if(orderEdited.getOrderId() == onlyOrder.getOrderId() && date.equals(onlyOrder.getDate())) {
            // return previous Order
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public Order getOrderByIdAndDate(int orderId, LocalDate date) throws DataPersistanceException {
        if(orderId == onlyOrder.getOrderId() && date.equals(onlyOrder.getDate())) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public void exportAll() throws DataPersistanceException {
        // do nothing...
    }

    @Override
    public int getLatestOrderId() throws DataPersistanceException {
        return onlyOrder.getOrderId();
    }
}
