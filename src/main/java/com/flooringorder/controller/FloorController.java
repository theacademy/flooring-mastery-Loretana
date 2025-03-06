package com.flooringorder.controller;

import com.flooringorder.dao.DataPersistanceException;
import com.flooringorder.model.Order;
import com.flooringorder.model.Product;
import com.flooringorder.service.FlooringService;
import com.flooringorder.service.InvalidOrderInformationException;
import com.flooringorder.service.InvalidTaxInformationException;
import com.flooringorder.service.OrderNotFoundException;
import com.flooringorder.ui.FlooringView;
import com.flooringorder.ui.InvalidUserInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Component
public class FloorController {

    private FlooringView view;
    private FlooringService service;

    @Autowired
    public FloorController(FlooringView view, FlooringService service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        while(keepGoing) {
            try {
                menuSelection = view.printMenuAndGetSelection();
                switch (menuSelection) {
                    case 1:
                        displayOrder();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        exportAllData();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                }
            } catch (OrderNotFoundException | DataPersistanceException | InvalidUserInputException |
                     InvalidTaxInformationException | InvalidOrderInformationException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }
    }

    private void displayOrder() throws OrderNotFoundException, DataPersistanceException, InvalidUserInputException {
        view.displayBanner();
        LocalDate date = view.getUserDateChoice();
        List<Order> orderFound = service.getOrdersByDate(date);
        view.displayAllOrders(orderFound);
    }


    private void addOrder() throws InvalidUserInputException, DataPersistanceException, InvalidTaxInformationException, InvalidOrderInformationException {
        view.displayAddOrderBanner();
        LocalDate dateFromUser = view.getUserDateChoice();
        String customerNameFromUser = view.getUserCustomerNameChoice().trim();
        String stateNameFromUser = view.getUserStateChoice();
        BigDecimal areaFromUser = new BigDecimal(view.getUserAreaChoice()).setScale(2, RoundingMode.HALF_UP);
        List<Product> productsList = service.getAllProduct();
        int productOptionFromUser = view.getUserProductTypeByNumberChoice(productsList);

        // adjust index to zero-based
        String productType =  productsList.get(productOptionFromUser - 1).getProductType();

        int latestOrderId = service.getLatestOrderId();
        Order newOrder = new Order(dateFromUser, latestOrderId+1);

        newOrder.setCustomerName(customerNameFromUser);
        newOrder.setState(stateNameFromUser);
        newOrder.setProductType(productType);
        newOrder.setArea(areaFromUser);

        if(service.validateOrderInfo(newOrder)) {
            service.calculateOrderCost(newOrder);
            if(view.getUserConfirmation(newOrder)) {
                service.addOrder(newOrder, dateFromUser);
            }
        }

    }

    private void editOrder() throws InvalidUserInputException, DataPersistanceException, OrderNotFoundException, InvalidTaxInformationException, InvalidOrderInformationException {
        view.displayEditOrderBanner();
        LocalDate userDate = view.getUserDateChoice();
        int userOrderId = view.getUserOrderIdChoice();
        Order orderFound = service.getOrder(userOrderId, userDate);
        boolean recalculate = false;

        // edit customer name
        String newCustomerName = view.getUserCustomerNameChoiceEdit(orderFound);
        if(!newCustomerName.isBlank()) {
            orderFound.setCustomerName(newCustomerName);
        }

        // edit state
        String newState = view.getUserStateNameChoiceEdit(orderFound);
        if(!newState.isBlank()) {
            orderFound.setState(newState);
            recalculate = true;
        }

        // edit product type
        List<Product> productsList = service.getAllProduct();
        String productOptionFromUser = view.getUserProductTypeByNumberChoiceEdit(orderFound, productsList);
        if(!productOptionFromUser.isBlank()) {
            int productIndex = Integer.parseInt(productOptionFromUser) - 1;
            orderFound.setProductType(productsList.get(productIndex).getProductType());
            recalculate = true;
        }

        // edit area size
        String area = view.getUserAreaChoiceEdit(orderFound);
        if(!area.isBlank()) {
            orderFound.setArea(new BigDecimal(area).setScale(2, RoundingMode.HALF_UP));
            recalculate = true;
        }

        if(service.validateOrderInfo(orderFound)) {
            if(recalculate) {
                service.calculateOrderCost(orderFound);
            }
            if(view.getUserConfirmation(orderFound)) {
                service.editOrder(userDate, orderFound);
            }
        }


    }

    private void removeOrder() {
        System.out.println("removeOrder");

    }

    private void exportAllData() {
        System.out.println("exportAllData");

    }

    private void quit() {
        System.out.println("quit");

    }



}
