package com.flooringorder.service;

import com.flooringorder.dao.DataPersistanceException;
import com.flooringorder.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.core.Local;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class FlooringServiceImplTest {

    private static final DateTimeFormatter FORMATTER_ISO = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    FlooringService service;

    public FlooringServiceImplTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("serviceLayer", FlooringService.class);
    }

    /*
    * Adding an order with a date in the past should throw an Exception
    * */
    @Test
    void testAddOrderWithPastDate() throws Exception {
        int orderId = 1;
        LocalDate orderDate = LocalDate.parse("1999-01-01", FORMATTER_ISO);
        Order order = new Order(orderDate, orderId);
        order = new Order(orderDate, orderId);
        order.setCustomerName("Ada Lovelace");
        order.setState("Texas");
        order.setArea(new BigDecimal("249.00"));
        order.setProductType("Tile");

        try {
            service.validateOrderInfo(order);
            fail("Expected Invalid Order Information (Date must be in the future) Exception was not thrown.");
        } catch (InvalidTaxInformationException | DataPersistanceException e) {
            fail("Incorrect exception was thrown.");
        } catch(InvalidOrderInformationException e) {
            // Expected to come here
        }
    }

    /*
     * Adding an Order with a future date should not throw an Exception
     * */
    @Test
    void testAddOrderWithFutureDate() throws Exception {
        int orderId = 1;
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate orderDate = tomorrow;
        Order order = new Order(orderDate, orderId);
        order = new Order(orderDate, orderId);
        order.setCustomerName("Ada Lovelace");
        order.setState("Texas");
        order.setArea(new BigDecimal("249.00"));
        order.setProductType("Tile");

        try {
            service.validateOrderInfo(order);
        } catch(InvalidOrderInformationException e) {
            fail("No exception should have been thrown.");
        }
    }

    /*
     * Adding an Order with today date should throw an Exception
     * */
    @Test
    void testAddOrderWithTodayDate() throws Exception {
        int orderId = 1;
        LocalDate orderDate = LocalDate.now();
        Order order = new Order(orderDate, orderId);
        order = new Order(orderDate, orderId);
        order.setCustomerName("Ada Lovelace");
        order.setState("Texas");
        order.setArea(new BigDecimal("249.00"));
        order.setProductType("Tile");

        try {
            service.validateOrderInfo(order);
            fail("Exception should have been thrown.");
        } catch(InvalidOrderInformationException e) {
        }
    }

    /*
    * A blank or null customer name should throw an Exception
    * */
    @Test
    void testAddOrderWithBlankOrNullCustomerName() throws Exception {
        int orderId = 1;
        LocalDate orderDate = LocalDate.now().plusDays(5);
        Order order = new Order(orderDate, orderId);
        order = new Order(orderDate, orderId);
        order.setCustomerName("      ");
        order.setState("Texas");
        order.setArea(new BigDecimal("249.00"));
        order.setProductType("Tile");

        try {
            service.validateOrderInfo(order);
            fail("Exception should have been thrown.");
        } catch (InvalidTaxInformationException | DataPersistanceException e) {
            fail("Incorrect exception was thrown.");
        } catch(InvalidOrderInformationException e) {}

        // set customer name to null
        order.setCustomerName(null);

        try {
            service.validateOrderInfo(order);
            fail("Exception should have been thrown.");
        } catch (InvalidTaxInformationException | DataPersistanceException e) {
            fail("Incorrect exception was thrown.");
        } catch(InvalidOrderInformationException e) {}

    }

    /*
     * A customer name that contains invalid character should throw an Exception
     * */
    @Test
    void testAddOrderWithInvalidCharacterCustomerName() throws Exception {
        int orderId = 1;
        LocalDate orderDate = LocalDate.now().plusDays(5);
        Order order = new Order(orderDate, orderId);
        order = new Order(orderDate, orderId);
        order.setCustomerName("$Peter_,Parker!");
        order.setState("Texas");
        order.setArea(new BigDecimal("249.00"));
        order.setProductType("Tile");

        try {
            service.validateOrderInfo(order);
            fail("Exception should have been thrown.");
        } catch (InvalidTaxInformationException | DataPersistanceException e) {
            fail("Incorrect exception was thrown.");
        } catch(InvalidOrderInformationException e) {

        }
    }

    /*
     * A valid customer name may contains letter, digit, period or coma should not throw an Exception
     * */
    @Test
    void testAddOrderWithValidCustomerName() throws Exception {
        int orderId = 1;
        LocalDate orderDate = LocalDate.now().plusDays(5);
        Order order = new Order(orderDate, orderId);
        order = new Order(orderDate, orderId);
        order.setCustomerName("Peter, Parker.");
        order.setState("Texas");
        order.setArea(new BigDecimal("249.00"));
        order.setProductType("Tile");

        try {
            service.validateOrderInfo(order);
            order.setCustomerName("Peter812");
            service.validateOrderInfo(order);
            order.setCustomerName("777");
            service.validateOrderInfo(order);
            order.setCustomerName("...,,,");
            service.validateOrderInfo(order);
        } catch(InvalidOrderInformationException e) {
            fail("No exception should have been thrown.");
        }
    }

    /*
     * A nonexistent state should throw an Exception
     * */
    @Test
    void testAddOrderWithInvalidState() throws Exception {
        int orderId = 1;
        LocalDate orderDate = LocalDate.now().plusDays(5);
        Order order = new Order(orderDate, orderId);
        order = new Order(orderDate, orderId);
        order.setCustomerName("Peter, Parker.");
        order.setState("Quebec");
        order.setArea(new BigDecimal("249.00"));
        order.setProductType("Tile");

        try {
            service.validateOrderInfo(order);
            fail("Exception should have been thrown.");
        } catch (DataPersistanceException | InvalidOrderInformationException e) {
            fail("Incorrect exception was thrown.");
        } catch(InvalidTaxInformationException e) {
        }
    }

    /*
     * A state that already exists in memory should not throw an Exception
     * */
    @Test
    void testAddOrderWithValidState() throws Exception {
        int orderId = 1;
        LocalDate orderDate = LocalDate.now().plusDays(5);
        Order order = new Order(orderDate, orderId);
        order = new Order(orderDate, orderId);
        order.setCustomerName("Peter, Parker.");
        order.setState("Texas");
        order.setArea(new BigDecimal("249.00"));
        order.setProductType("Tile");

        try {
            service.validateOrderInfo(order);
        } catch (Exception e) {
            fail("No exception should have been thrown");
        }
    }

    /*
     * A negative area should throw an Exception
     * */
    @Test
    void testAddOrderWithNegativeArea() throws Exception {
        int orderId = 1;
        LocalDate orderDate = LocalDate.now().plusDays(5);
        Order order = new Order(orderDate, orderId);
        order = new Order(orderDate, orderId);
        order.setCustomerName("Peter, Parker.");
        order.setState("Texas");
        order.setArea(new BigDecimal("-1"));
        order.setProductType("Tile");

        try {
            service.validateOrderInfo(order);
            fail("Exception should have been thrown.");
        } catch (InvalidTaxInformationException | DataPersistanceException e) {
            fail("Incorrect exception was thrown.");
        } catch (InvalidOrderInformationException e) {

        }
    }

    /*
     * A area size under 100 should throw an Exception
     * */
    @Test
    void testAddOrderWithUnderSizeArea() throws Exception {
        int orderId = 1;
        LocalDate orderDate = LocalDate.now().plusDays(5);
        Order order = new Order(orderDate, orderId);
        order = new Order(orderDate, orderId);
        order.setCustomerName("Peter, Parker.");
        order.setState("Texas");
        order.setArea(new BigDecimal("99.99"));
        order.setProductType("Tile");

        try {
            service.validateOrderInfo(order);
            fail("Exception should have been thrown.");
        } catch (InvalidTaxInformationException | DataPersistanceException e) {
            fail("Incorrect exception was thrown.");
        } catch (InvalidOrderInformationException e) {

        }
    }


    /*
    * Editing a new name should not change any cost
    * */
    @Test
    void testEditOrderCustomerNameOnly() throws Exception {
        Order orderSelected = service.getOrder(1, LocalDate.parse("2030-01-01"));
        BigDecimal oldMaterialCost = orderSelected.getMaterialCost();
        BigDecimal oldLaborCost = orderSelected.getLaborCost();
        BigDecimal oldTax = orderSelected.getTax();
        BigDecimal oldTotal = orderSelected.getTotal();

        orderSelected.setCustomerName("Bob");
        BigDecimal total = BigDecimal.ZERO;
        if(service.validateOrderInfo(orderSelected)) {
            total = service.calculateOrderCost(orderSelected);
        }

        assertEquals(oldMaterialCost, orderSelected.getMaterialCost(), "Material cost should be the same.");
        assertEquals(oldLaborCost, orderSelected.getLaborCost(), "Labor cost should be the same.");
        assertEquals(oldTax, orderSelected.getTax(), "Tax cost should be the same.");
        assertEquals(oldTotal, total, "Total cost should be the same.");
    }

    /*
     * Editing an area should change the cost
     * */
    @Test
    void testEditOrderArea() throws Exception {
        Order orderSelected = service.getOrder(1, LocalDate.parse("2030-01-01"));
        BigDecimal oldMaterialCost = orderSelected.getMaterialCost();
        BigDecimal oldLaborCost = orderSelected.getLaborCost();
        BigDecimal oldTax = orderSelected.getTax();
        BigDecimal oldTotal = orderSelected.getTotal();

        // edit area
        orderSelected.setArea(new BigDecimal("100"));
        BigDecimal total = BigDecimal.ZERO;
        if(service.validateOrderInfo(orderSelected)) {
            total = service.calculateOrderCost(orderSelected);
        }

        assertNotEquals(oldMaterialCost, orderSelected.getMaterialCost(), "Material cost should be different.");
        assertNotEquals(oldLaborCost, orderSelected.getLaborCost(), "Labor cost should be different.");
        assertNotEquals(oldTax, orderSelected.getTax(), "Tax cost should be different.");
        assertNotEquals(oldTotal, total, "Total cost should be different.");

    }

    /*
     * Editing state should change the cost
     * */
    @Test
    void testEditOrderState() throws Exception {
        Order orderSelected = service.getOrder(1, LocalDate.parse("2030-01-01"));
        BigDecimal oldMaterialCost = orderSelected.getMaterialCost();
        BigDecimal oldLaborCost = orderSelected.getLaborCost();
        BigDecimal oldTax = orderSelected.getTax();
        BigDecimal oldTotal = orderSelected.getTotal();
        BigDecimal oldTaxRate = orderSelected.getTaxRate();


        // edit state
        orderSelected.setState("Kentucky");
        BigDecimal total = BigDecimal.ZERO;
        if(service.validateOrderInfo(orderSelected)) {
            total = service.calculateOrderCost(orderSelected);
        }

        assertNotEquals(oldTaxRate, orderSelected.getTaxRate(), "TaxRate should be different.");
        assertEquals(oldMaterialCost, orderSelected.getMaterialCost(), "Material cost should be the same.");
        assertEquals(oldLaborCost, orderSelected.getLaborCost(), "Labor cost should be the same.");
        assertNotEquals(oldTax, orderSelected.getTax(), "Tax cost should be different.");
        assertNotEquals(oldTotal, total, "Total cost should be different.");


    }

    /*
     * Editing product type should change the cost
     * */
    @Test
    void testEditOrderProductType() throws Exception {
        Order orderSelected = service.getOrder(1, LocalDate.parse("2030-01-01"));
        BigDecimal oldMaterialCost = orderSelected.getMaterialCost();
        BigDecimal oldLaborCost = orderSelected.getLaborCost();
        BigDecimal oldTax = orderSelected.getTax();
        BigDecimal oldTotal = orderSelected.getTotal();

        BigDecimal oldCostPerSquareFoot = orderSelected.getCostPerSquareFoot();
        BigDecimal oldLaborCostPerSquareFoot = orderSelected.getLaborCostPerSquareFoot();
        String oldProductType = orderSelected.getProductType();

        // edit area
        orderSelected.setProductType("Wood");
        BigDecimal total = BigDecimal.ZERO;
        if(service.validateOrderInfo(orderSelected)) {
            total = service.calculateOrderCost(orderSelected);
        }

        assertNotEquals(oldMaterialCost, orderSelected.getMaterialCost(), "Material cost should be different.");
        assertNotEquals(oldLaborCost, orderSelected.getLaborCost(), "Labor cost should be different.");
        assertNotEquals(oldTax, orderSelected.getTax(), "Tax cost should be different.");
        assertNotEquals(oldTotal, total, "Total cost should be different.");
        assertNotEquals(orderSelected.getProductType(), oldProductType, "Product type should be different");
        assertNotEquals(orderSelected.getCostPerSquareFoot(), oldCostPerSquareFoot, "CostPerSquareFoot should be different");
        assertNotEquals(orderSelected.getLaborCostPerSquareFoot(), oldLaborCostPerSquareFoot, "LaborCostPerSquareFoot should be different");

    }

    /*
    * Removing a nonexistent order should return null
    * */
    @Test
    void testRemoveOrder() throws DataPersistanceException {
        LocalDate date = LocalDate.parse("2030-01-01");
        Order testClone = new Order(date, 1);
        testClone.setProductType("Tile");
        testClone.setState("Texas");
        testClone.setCustomerName("Ada Lovelace");
        testClone.setArea(new BigDecimal("249.00"));

        Order shouldBeAda = service.removeOrder(1, date);
        assertNotNull(shouldBeAda, "Removing Order #1 should be not null");
        assertEquals(shouldBeAda.getOrderId(), testClone.getOrderId(), "Order id should be the same");
        assertEquals(shouldBeAda.getState(), testClone.getState(), "State should be the same");
        assertEquals(shouldBeAda.getProductType(), testClone.getProductType(), "Product Type should be the same");

        Order shouldBeNull = service.removeOrder(99, date);
        assertNull(shouldBeNull);
    }


}