package com.flooringorder.service;

import com.flooringorder.dao.DataPersistanceException;
import com.flooringorder.model.Order;
import com.flooringorder.model.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface FlooringService {

    /**
     * Add a new Order obj based on the given date.
     *
     * @param newOrder obj that will be added
     * @param date with which the order is associated
     * @return the previous Order object that has been replaced
     * if it exists, null otherwise
     * @throws InvalidOrderInformationException
     * @throws DataPersistanceException
     * @throws InvalidTaxInformationException
     */
    Order addOrder(Order newOrder, LocalDate date) throws InvalidTaxInformationException, DataPersistanceException, InvalidOrderInformationException;

    /**
     * Save an edited Order associated with the given date.
     *
     * @param date of the edited order
     * @param order object to be saved
     * @return the previous Order object that has been replaced
     * if it exists, null otherwise
     * @throws DataPersistanceException
     */
    Order editOrder(LocalDate date, Order order) throws DataPersistanceException;

    /**
     * Validate all business logic on the given Order object.
     *
     * @param order of the order to remove
     * @return true if order has a valid area, state, customer name and product
     * false otherwise
     * @throws InvalidOrderInformationException
     * @throws DataPersistanceException
     * @throws InvalidTaxInformationException
     */
    boolean validateOrderInfo(Order order) throws InvalidOrderInformationException, DataPersistanceException, InvalidTaxInformationException;

    /**
     * Remove an Order associated with the given id and date.
     *
     * @param orderId of the order to remove
     * @param date of the order to remove
     * @return the removed Order associated with the id and date
     * if it exists, null otherwise
     * @throws DataPersistanceException
     */
    Order removeOrder(int orderId, LocalDate date) throws DataPersistanceException;

    /**
     * Retrieve an Order associated with the given id and date.
     *
     * @param orderId of the order to retrieve
     * @param date of the order to retrieve
     * @return Order associated with the given id and date
     * if it exists, null otherwise
     * @throws DataPersistanceException
     * @throws OrderNotFoundException
     */
    Order getOrder(int orderId, LocalDate date) throws DataPersistanceException, OrderNotFoundException;

    /**
     * Return all Order associated with the given date.
     *
     * @param date with which the order is associated
     * @return a list of Order associated with the Date
     * if it exists, null otherwise
     * @throws DataPersistanceException
     * @throws OrderNotFoundException
     */
    List<Order> getOrdersByDate(LocalDate date) throws DataPersistanceException, OrderNotFoundException;

    /**
     * Return a list of all Product obj.
     *
     * @return a list of all Product obj
     * if it exists, empty list otherwise
     * @throws DataPersistanceException
     */
    List<Product> getAllProduct() throws DataPersistanceException;

    /**
     * Retrieves the highest order ID from all stored orders
     *
     * @return the highest order ID from all stored orders
     * if it exists, 0 otherwise
     * @throws DataPersistanceException
     */
    int getLatestOrderId() throws DataPersistanceException;


    /**
     * Return the total cost of the given Order obj
     *
     * @param order that will calculate all cost
     * @return the total cost of the given Order obj
     */
    BigDecimal calculateOrderCost(Order order);

    /**
     * Save All Orders into Backup/DataExport.txt file.
     *
     * @throws DataPersistanceException
     */
    void exportAll() throws DataPersistanceException;


}
