package com.flooringorder.dao;

import com.flooringorder.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
class OrderDaoFileImplTest {

    OrderDaoFileImpl testOrderDao;
    private static final DateTimeFormatter FORMATTER_ISO = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @BeforeEach
    public void setUp() throws IOException {
        String testOrderDirectory = "src/main/java/com/flooringorder/test/Orders/";
        String testExportDirectoryFile = "src/main/java/com/flooringorder/test/Backup/DataExportTest.txt";
        // Use the FileWriter to quickly blank the file
        new FileWriter(testExportDirectoryFile);
        testOrderDao = new OrderDaoFileImpl(testOrderDirectory, testExportDirectoryFile);
    }


    @Test
    void testAddOrder() throws DataPersistanceException {
        int orderId = 1;
        LocalDate orderDate = LocalDate.parse("2030-01-01", FORMATTER_ISO);
        Order order = new Order(orderDate, orderId);

        order.setCustomerName("Peter");
        order.setState("Texas");
        order.setArea(new BigDecimal("249.00"));
        order.setProductType("Tile");
        order.setTaxRate(new BigDecimal("4.45"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        order.setMaterialCost(new BigDecimal("871.50"));
        order.setLaborCost(new BigDecimal("1033.35"));
        order.setTax(new BigDecimal("84.77"));
        order.setTotal(new BigDecimal("1989.62"));

        testOrderDao.addOrder(order, orderDate);
        Order shouldBePeterOrder = testOrderDao.getOrderByIdAndDate(1, orderDate);

        assertNotNull(shouldBePeterOrder, "Peter Order should exists");


    }

    @Test
    void getAllOrderByDate() {
    }

    @Test
    void removeOrder() {
    }

    @Test
    void updateOrder() {
    }

    @Test
    void getOrderByIdAndDate() {
    }

    @Test
    void exportAll() {
    }

    @Test
    void getLatestOrderId() {
    }
}