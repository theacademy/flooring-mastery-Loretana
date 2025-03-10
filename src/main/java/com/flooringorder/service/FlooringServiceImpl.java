package com.flooringorder.service;

import com.flooringorder.dao.DataPersistanceException;
import com.flooringorder.dao.OrderDao;
import com.flooringorder.dao.ProductDao;
import com.flooringorder.dao.TaxDao;
import com.flooringorder.model.Order;
import com.flooringorder.model.Product;
import com.flooringorder.model.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Component
public class FlooringServiceImpl implements FlooringService {

    private TaxDao taxDao;
    private ProductDao productDao;
    private OrderDao orderDao;

    private static final BigDecimal AREA_MIN_SIZE = BigDecimal.valueOf(100);

    @Autowired
    public FlooringServiceImpl(TaxDao taxDao, ProductDao productDao, OrderDao orderDao) {
        this.taxDao = taxDao;
        this.productDao = productDao;
        this.orderDao = orderDao;
    }

    @Override
    public Order addOrder(Order newOrder, LocalDate date) throws DataPersistanceException {
        return orderDao.addOrder(newOrder, date);
    }

    @Override
    public Order editOrder(LocalDate date, Order order) throws DataPersistanceException {
        return orderDao.updateOrder(order, date);
    }

    @Override
    public Order removeOrder(int orderId, LocalDate date) throws DataPersistanceException {
        return orderDao.removeOrder(orderId, date);
    }

    @Override
    public Order getOrder(int orderId, LocalDate date) throws DataPersistanceException, OrderNotFoundException {
        Order orderFound = orderDao.getOrderByIdAndDate(orderId, date);
        if(orderFound == null) {
            throw new OrderNotFoundException("ERROR: No orders found matching the specified date and ID.");
        }
        return orderDao.getOrderByIdAndDate(orderId, date);
    }

    /*
     *  Retrieve a list of Order object based on a date
     *  Return null if nothing found
     * */
    @Override
    public List<Order> getOrdersByDate(LocalDate userDate) throws OrderNotFoundException, DataPersistanceException {
        List<Order> ordersFound = orderDao.getAllOrderByDate(userDate);
        if(ordersFound == null) {
            throw new OrderNotFoundException("ERROR: No orders found on this date.");
        }
        return ordersFound;
    }

    @Override
    public List<Product> getAllProduct() throws DataPersistanceException {
        return productDao.getAllProduct();
    }

    @Override
    public int getLatestOrderId() throws DataPersistanceException {
        return orderDao.getLatestOrderId();
    }

    @Override
    public BigDecimal calculateOrderCost(Order order) {
        if(order == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal area = order.getArea();
        BigDecimal costPerSquareFoot = order.getCostPerSquareFoot();
        BigDecimal laborCostPerSquareFoot = order.getLaborCostPerSquareFoot();
        BigDecimal taxRate = order.getTaxRate();

        order.setMaterialCost(area.multiply(costPerSquareFoot).setScale(2, RoundingMode.HALF_UP));
        order.setLaborCost(area.multiply(laborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP));
        BigDecimal totalWithoutTax = order.getMaterialCost().add(order.getLaborCost()).setScale(2, RoundingMode.HALF_UP);
        // setting scale to 5 for better precision
        BigDecimal taxRateDividedByHundred = taxRate.divide(new BigDecimal("100"), 5, RoundingMode.HALF_UP);
        BigDecimal tax = totalWithoutTax.multiply(taxRateDividedByHundred).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = tax.add(totalWithoutTax).setScale(2, RoundingMode.HALF_UP);
        order.setTax(tax);
        order.setTotal(total);
        return total;
    }

    @Override
    public void exportAll() throws DataPersistanceException {
        orderDao.exportAll();
    }

    @Override
    public void validateCustomerName(Order order, String newCustomerName) throws InvalidOrderInformationException {
        // CustomerName may not be blank
        if(newCustomerName == null || newCustomerName.isBlank()) {
            throw new InvalidOrderInformationException("ERROR: Customer cannot be blank.");
        }

        // CustomerName limited to [a-z][0-9] and periods and comma characters and spaces between
        String regex = "^[a-zA-Z0-9,.]+(\\s+[a-zA-Z0-9,.]+)*$";
        if(!newCustomerName.matches(regex)) {
            throw new InvalidOrderInformationException("ERROR: Customer is limited to [a-z][0-9] and periods and comma characters.");
        }
        order.setCustomerName(newCustomerName);
    }

    @Override
    public void validateState(Order order, String newState) throws InvalidTaxInformationException, DataPersistanceException {
        // State must exist
        Tax tax = taxDao.getTaxByNameAbbrev(newState);
        if(tax == null) {
            throw new InvalidTaxInformationException("ERROR: State doesn't exist.");
        }
        order.setState(tax.getStateAbbreviation());
        order.setTaxRate(tax.getTaxRate());
    }

    @Override
    public void validateArea(Order order, String area) throws InvalidOrderInformationException {
        // area size must be positive
        BigDecimal areaBigDecimal = new BigDecimal(area).setScale(2, RoundingMode.HALF_UP);

        if(areaBigDecimal.compareTo(BigDecimal.ZERO) < 1) {
            throw new InvalidOrderInformationException("ERROR: Area must be a positive value.");
        }

        // area size must be equal or above 100
        if(areaBigDecimal.compareTo(AREA_MIN_SIZE) < 0) {
            throw new InvalidOrderInformationException("ERROR: Area must have a minimum size of 100.");
        }
        order.setArea(areaBigDecimal);
    }

    @Override
    public void validateDate(LocalDate dateFromUser) throws InvalidOrderInformationException {
        // Date must be in the future
        if(dateFromUser.compareTo(LocalDate.now()) < 1) {
            throw new InvalidOrderInformationException("ERROR: Date must be in the future.");
        }
    }
}
