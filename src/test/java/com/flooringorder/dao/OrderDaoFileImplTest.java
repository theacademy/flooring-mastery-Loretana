package com.flooringorder.dao;

import com.flooringorder.model.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderDaoFileImplTest {

    OrderDaoFileImpl testOrderDao;

    public final static String TEST_ORDER_DIRECTORY = "src/test/resources/Test/Orders/";
    public final static String TEST_EXPORT_FILE = "DataExportTest.txt";
    public final static String TEST_EXPORT_DIRECTORY = "src/test/resources/Test/Backup/";

    @BeforeEach
    public void setUp() throws IOException {
       testOrderDao = new OrderDaoFileImpl(TEST_ORDER_DIRECTORY, TEST_EXPORT_DIRECTORY + TEST_EXPORT_FILE);

       File orderDirectoryTest = new File(TEST_ORDER_DIRECTORY);
       File exportDirectoryTest = new File(TEST_EXPORT_DIRECTORY);
       if(!orderDirectoryTest.exists()) {
           orderDirectoryTest.mkdir();
       }
       if(!exportDirectoryTest.exists()) {
           exportDirectoryTest.mkdir();
       }

    }

    /*
    * Delete all order test file created
    * */
    @AfterEach
    public void tearDown() throws IOException {
        File orderDirectoryTest = new File(TEST_ORDER_DIRECTORY);
        File[] ordersTestFiles = orderDirectoryTest.listFiles();
        if(ordersTestFiles != null) {
            for(File currentFile: ordersTestFiles) {
                    currentFile.delete();
            }
        }
        File exportDirectoryTestFile = new File(TEST_EXPORT_DIRECTORY+TEST_EXPORT_FILE);
        exportDirectoryTestFile.delete();
    }

    @Test
    void testAddOrderAndGetAllOrderByDate() throws DataPersistanceException {
        int orderId = 1;
        LocalDate date = LocalDate.parse("2030-01-01");

        Order peterOrder = new Order(date, orderId);
        peterOrder.setCustomerName("Peter");
        peterOrder.setState("Texas");
        peterOrder.setArea(new BigDecimal("249.00"));
        peterOrder.setProductType("Tile");
        peterOrder.setTaxRate(new BigDecimal("4.45"));
        peterOrder.setCostPerSquareFoot(new BigDecimal("3.50"));
        peterOrder.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        peterOrder.setMaterialCost(new BigDecimal("871.50"));
        peterOrder.setLaborCost(new BigDecimal("1033.35"));
        peterOrder.setTax(new BigDecimal("84.77"));
        peterOrder.setTotal(new BigDecimal("1989.62"));

        Order previousOrder = testOrderDao.addOrder(peterOrder, date);
        assertNull(previousOrder, "PreviousOrder should be null because there's no previous order assign to PeterOrder");
        Order shouldBePeterOrder = testOrderDao.getOrderByIdAndDate(1, date);

        assertNotNull(shouldBePeterOrder, "Peter Order should exists");
        List<Order> orders = testOrderDao.getAllOrderByDate(date);
        assertEquals(1, orders.size(), "Should only contains Peter Order");

        Order bobOrder = new Order(date, 2);
        bobOrder.setCustomerName("Bob");
        bobOrder.setState("Texas");
        bobOrder.setArea(new BigDecimal("100.00"));
        bobOrder.setProductType("Wood");
        bobOrder.setTaxRate(new BigDecimal("4.45"));
        bobOrder.setCostPerSquareFoot(new BigDecimal("5.15"));
        bobOrder.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        bobOrder.setMaterialCost(new BigDecimal("515.00"));
        bobOrder.setLaborCost(new BigDecimal("475.00"));
        bobOrder.setTax(new BigDecimal("44.06"));
        bobOrder.setTotal(new BigDecimal("1034.06"));

        testOrderDao.addOrder(bobOrder, date);
        orders = testOrderDao.getAllOrderByDate(date);

        assertEquals(2, orders.size(), "Should contains Peter & Bob Order");
        assertTrue(orders.contains(peterOrder), "The list of orders should include Peter.");
        assertTrue(orders.contains(bobOrder), "The list of orders should include Bob");
    }


    @Test
    void removeOrder() throws DataPersistanceException {
        LocalDate date = LocalDate.parse("2028-03-04");

        Order bobbyOrder = new Order(date, 5);
        bobbyOrder.setCustomerName("Bobby");
        bobbyOrder.setState("Texas");
        bobbyOrder.setArea(new BigDecimal("249.00"));
        bobbyOrder.setProductType("Tile");
        bobbyOrder.setTaxRate(new BigDecimal("4.45"));
        bobbyOrder.setCostPerSquareFoot(new BigDecimal("3.50"));
        bobbyOrder.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        bobbyOrder.setMaterialCost(new BigDecimal("871.50"));
        bobbyOrder.setLaborCost(new BigDecimal("1033.35"));
        bobbyOrder.setTax(new BigDecimal("84.77"));
        bobbyOrder.setTotal(new BigDecimal("1989.62"));

        testOrderDao.addOrder(bobbyOrder, date);
        Order shouldBeBobbyOrder = testOrderDao.getOrderByIdAndDate(5, date);

        assertNotNull(shouldBeBobbyOrder, "Bobby Order should exists");
        List<Order> orders = testOrderDao.getAllOrderByDate(date);
        assertEquals(1, orders.size(), "Should only contains Bobby Order");

        Order removed = testOrderDao.removeOrder(5, date);

        assertEquals(5, removed.getOrderId(), "Removed order id should be 5");
        assertEquals(bobbyOrder.getCustomerName(), removed.getCustomerName(), "Removed order name should be Bobby");
        Order shouldBeNull = testOrderDao.getOrderByIdAndDate(5, date);
        assertNull(shouldBeNull, "Order should not exists");

    }


    @Test
    void updateOrder() throws DataPersistanceException {

        LocalDate date = LocalDate.parse("2029-04-26");
        int initialOrderId = 7;
        String initialCustomerName = "Bobby";
        String initialState = "Texas";
        BigDecimal initialAreaSize = new BigDecimal("249.00");

        Order bobbyOrder = new Order(date, 7);
        bobbyOrder.setCustomerName(initialCustomerName);
        bobbyOrder.setState(initialState);
        bobbyOrder.setArea(initialAreaSize);

        bobbyOrder.setProductType("Tile");
        bobbyOrder.setTaxRate(new BigDecimal("4.45"));
        bobbyOrder.setCostPerSquareFoot(new BigDecimal("3.50"));
        bobbyOrder.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        bobbyOrder.setMaterialCost(new BigDecimal("871.50"));
        bobbyOrder.setLaborCost(new BigDecimal("1033.35"));
        bobbyOrder.setTax(new BigDecimal("84.77"));
        bobbyOrder.setTotal(new BigDecimal("1989.62"));

        testOrderDao.addOrder(bobbyOrder, date);
        Order shouldBeBobbyOrder = testOrderDao.getOrderByIdAndDate(7, date);
        assertNotNull(shouldBeBobbyOrder, "Bobby Order should exists");

        bobbyOrder.setArea(new BigDecimal(100));
        bobbyOrder.setCustomerName("Bobby Junior");;

        Order lastOrderVersion = testOrderDao.updateOrder(bobbyOrder, date);

        // Validate older version match the pre-edited version
        assertEquals(initialOrderId, lastOrderVersion.getOrderId(), "Previous order id should be 7 as an id");
        assertEquals(initialState, lastOrderVersion.getState(), "Previous order state should be Texas");
        assertEquals(initialCustomerName, lastOrderVersion.getCustomerName(), "Previous order customer name should be Bobby");
        assertEquals(initialAreaSize, lastOrderVersion.getArea(), "Previous order customer name should be Bobby");

        // Validate update has been made
        Order editedOrder = testOrderDao.getOrderByIdAndDate(7, date);
        assertNotEquals(initialAreaSize, editedOrder.getArea(), "Edited Order should have a different Area size");
        assertNotEquals(initialCustomerName, editedOrder.getCustomerName(), "Edited Order should have a different customer name");
        assertEquals("Bobby Junior", editedOrder.getCustomerName(), "New customer name should be Bobby Junior");
        assertEquals("100.00", editedOrder.getArea().toString(), "New area size should be 100");

    }

    @Test
    void getOrderByIdAndDate() throws DataPersistanceException {
        LocalDate date = LocalDate.parse("2030-12-25");
        Order lebronOrder = new Order(date, 23);
        lebronOrder.setCustomerName("LeBron James");
        lebronOrder.setState("Texas");
        lebronOrder.setArea(new BigDecimal("100.00"));
        lebronOrder.setProductType("Wood");
        lebronOrder.setTaxRate(new BigDecimal("4.45"));
        lebronOrder.setCostPerSquareFoot(new BigDecimal("5.15"));
        lebronOrder.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        lebronOrder.setMaterialCost(new BigDecimal("515.00"));
        lebronOrder.setLaborCost(new BigDecimal("475.00"));
        lebronOrder.setTax(new BigDecimal("44.06"));
        lebronOrder.setTotal(new BigDecimal("1034.06"));

        testOrderDao.addOrder(lebronOrder, date);
        Order shouldBeLebonOrder = testOrderDao.getOrderByIdAndDate(23, date);
        assertEquals(lebronOrder, shouldBeLebonOrder, "Should be LeBron James Order");
    }

    @Test
    void exportAll() throws DataPersistanceException, IOException {
        LocalDate firstDate = LocalDate.parse("2030-10-13");
        Order lebronOrder = new Order(firstDate, 23);
        lebronOrder.setCustomerName("LeBron James");
        lebronOrder.setState("Texas");
        lebronOrder.setArea(new BigDecimal("100.00"));
        lebronOrder.setProductType("Wood");
        lebronOrder.setTaxRate(new BigDecimal("4.45"));
        lebronOrder.setCostPerSquareFoot(new BigDecimal("5.15"));
        lebronOrder.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        lebronOrder.setMaterialCost(new BigDecimal("515.00"));
        lebronOrder.setLaborCost(new BigDecimal("475.00"));
        lebronOrder.setTax(new BigDecimal("44.06"));
        lebronOrder.setTotal(new BigDecimal("1034.06"));

        LocalDate secondDate = LocalDate.parse("2030-10-17");
        Order peterOrder = new Order(secondDate, 24);
        peterOrder.setCustomerName("Peter");
        peterOrder.setState("Texas");
        peterOrder.setArea(new BigDecimal("249.00"));
        peterOrder.setProductType("Tile");
        peterOrder.setTaxRate(new BigDecimal("4.45"));
        peterOrder.setCostPerSquareFoot(new BigDecimal("3.50"));
        peterOrder.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        peterOrder.setMaterialCost(new BigDecimal("871.50"));
        peterOrder.setLaborCost(new BigDecimal("1033.35"));
        peterOrder.setTax(new BigDecimal("84.77"));
        peterOrder.setTotal(new BigDecimal("1989.62"));

        testOrderDao.addOrder(lebronOrder, firstDate);
        testOrderDao.addOrder(peterOrder, secondDate);

        testOrderDao.exportAll();
        long exportFileLineCount = Files.lines(Path.of(TEST_EXPORT_DIRECTORY + TEST_EXPORT_FILE)).count();
        assertEquals(3, exportFileLineCount, "Export file should contains 2 orders lines and 1 header");
    }

    @Test
    void getLatestOrderId() throws DataPersistanceException {
        LocalDate firstDate = LocalDate.parse("2027-07-21");
        Order lebronOrder = new Order(firstDate, 23);
        lebronOrder.setCustomerName("LeBron James");
        lebronOrder.setState("Texas");
        lebronOrder.setArea(new BigDecimal("100.00"));
        lebronOrder.setProductType("Wood");
        lebronOrder.setTaxRate(new BigDecimal("4.45"));
        lebronOrder.setCostPerSquareFoot(new BigDecimal("5.15"));
        lebronOrder.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        lebronOrder.setMaterialCost(new BigDecimal("515.00"));
        lebronOrder.setLaborCost(new BigDecimal("475.00"));
        lebronOrder.setTax(new BigDecimal("44.06"));
        lebronOrder.setTotal(new BigDecimal("1034.06"));

        LocalDate secondDate = LocalDate.parse("2027-08-03");
        Order peterOrder = new Order(secondDate, 27);
        peterOrder.setCustomerName("Peter");
        peterOrder.setState("Texas");
        peterOrder.setArea(new BigDecimal("249.00"));
        peterOrder.setProductType("Tile");
        peterOrder.setTaxRate(new BigDecimal("4.45"));
        peterOrder.setCostPerSquareFoot(new BigDecimal("3.50"));
        peterOrder.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        peterOrder.setMaterialCost(new BigDecimal("871.50"));
        peterOrder.setLaborCost(new BigDecimal("1033.35"));
        peterOrder.setTax(new BigDecimal("84.77"));
        peterOrder.setTotal(new BigDecimal("1989.62"));

        testOrderDao.addOrder(lebronOrder, firstDate);
        testOrderDao.addOrder(peterOrder, secondDate);
        int latestId = testOrderDao.getLatestOrderId();
        assertEquals(27, latestId, "Latest Order id should be 27");
    }
}