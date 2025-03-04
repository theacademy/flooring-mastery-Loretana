package com.flooringorder.dao;

import com.flooringorder.model.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Component
public class OrderDaoFileImpl implements OrderDao {

    private final String ORDER_DIRECTORY_PATH;
    private final String EXPORT_DIRECTORY_PATH;
    private static final String DELIMITER = ",";
    private HashMap<LocalDate, List<Order>> ordersMap = new HashMap<>();


    public OrderDaoFileImpl() {
        ORDER_DIRECTORY_PATH = "src/main/java/com/flooringorder/SampleFileData/Orders/";
        EXPORT_DIRECTORY_PATH = "src/main/java/com/flooringorder/SampleFileData/Export/";
    }

    public OrderDaoFileImpl(String ORDER_FILE_PATH, String EXPORT_DIRECTORY_PATH) {
        this.ORDER_DIRECTORY_PATH = ORDER_FILE_PATH;
        this.EXPORT_DIRECTORY_PATH = EXPORT_DIRECTORY_PATH;
    }

    @Override
    public Order addOrder(Order newOrder, LocalDate date) {
        return null;
    }

    @Override
    public List<Order> getAllOrderByDate(LocalDate date) {
        return List.of();
    }

    @Override
    public Order removeOrder(int orderId, LocalDate date) {
        return null;
    }

    @Override
    public Order updateOrder(int orderId, LocalDate date) {
        return null;
    }

    @Override
    public Order getOrderByIdAndDate(int orderId, LocalDate date) {
        return null;
    }

    @Override
    public void exportAll() {

    }



}
