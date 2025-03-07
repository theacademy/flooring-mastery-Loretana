package com.flooringorder.dao;

import com.flooringorder.model.Order;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

public interface OrderDao {

    /**
     * Add a new Order obj based on the given date.
     *
     * @param newOrder obj that will be added
     * @param date with which the order is associated
     * @return the previous Order object that has been replaced
     * if it exists, null otherwise
     * @throws DataPersistanceException
     */
    Order addOrder(Order newOrder, LocalDate date) throws DataPersistanceException;

    /**
     * Return all Order associated with the given date.
     *
     * @param date with which the order is associated
     * @return a list of Order associated with the Date
     * if it exists, null otherwise
     * @throws DataPersistanceException
     */
    List<Order> getAllOrderByDate(LocalDate date) throws DataPersistanceException;

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
     * Save an edited Order associated with the given date.
     *
     * @param orderEdited object to be saved
     * @param date of the edited order
     * @return the previous Order object that has been replaced
     * if it exists, null otherwise
     * @throws DataPersistanceException
     */
    Order updateOrder(Order orderEdited, LocalDate date) throws DataPersistanceException;

    /**
     * Return an Order associated with the given date and id.
     *
     * @param orderId of the order to retrieve
     * @param date of the order to retrieve
     * @return an Order associated with the orderId and date
     * if it exists, null otherwise
     * @throws DataPersistanceException
     */
    Order getOrderByIdAndDate(int orderId, LocalDate date) throws DataPersistanceException;

    /**
     * Save All Orders into Backup/DataExport.txt file.
     *
     * @throws DataPersistanceException
     */
    void exportAll() throws DataPersistanceException;

    /**
     * Retrieves the highest order ID from all stored orders
     *
     * @return the highest order ID from all stored orders
     * if it exists, 0 otherwise
     * @throws DataPersistanceException
     */
    int getLatestOrderId() throws DataPersistanceException;

}
