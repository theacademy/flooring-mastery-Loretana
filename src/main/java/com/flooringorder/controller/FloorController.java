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
        boolean hasError = false;
        LocalDate dateFromUser = null;

        // Retrieve future date from user
        do {
            dateFromUser = view.getUserDateChoice();
            try {
                service.validateDate(dateFromUser);
                hasError = false;
            } catch (InvalidOrderInformationException e) {
                view.displayErrorMessage(e.getMessage());
                hasError = true;
            }

        } while(hasError);


        int latestOrderId = service.getLatestOrderId();
        Order newOrder = new Order(dateFromUser, latestOrderId+1);


        // Retrieve customer name from user
        do {
            String customerNameFromUser = view.getUserCustomerNameChoice().trim();
            if(customerNameFromUser.isBlank()) {
                hasError = true;
                continue;
            }
            try {
                service.validateCustomerName(newOrder, customerNameFromUser);
                hasError = false;
            } catch (InvalidOrderInformationException e) {
                view.displayErrorMessage(e.getMessage());
                hasError = true;
            }

        } while(hasError);

        // Retrieve state from user
        do {
            String stateNameFromUser = view.getUserStateChoice().toUpperCase();
            if(stateNameFromUser.isBlank()) {
                hasError = true;
                continue;
            }
            try {
                service.validateState(newOrder, stateNameFromUser);
                hasError = false;
            } catch (InvalidTaxInformationException e) {
                view.displayErrorMessage(e.getMessage());
                hasError = true;
            }

        } while(hasError);


        // Retrieve area size from user
        do {
            String areaFromUser = view.getUserAreaChoice();
            if(areaFromUser.isBlank()) {
                hasError = true;
                continue;
            }
            try {
                service.validateArea(newOrder, areaFromUser);
                hasError = false;
            } catch (InvalidOrderInformationException e) {
                view.displayErrorMessage(e.getMessage());
                hasError = true;
            } catch (NumberFormatException e) {
                view.displayErrorMessage("ERROR: area must be a number.");
                hasError = true;
            }

        } while(hasError);

        // Get product type from user
        List<Product> productsList = service.getAllProduct();
        int productOptionFromUser = view.getUserProductTypeByNumberChoice(productsList);
        // adjust index to zero-based
        Product selectedProduct = productsList.get(productOptionFromUser - 1);
        newOrder.setProductType(selectedProduct.getProductType());
        newOrder.setLaborCostPerSquareFoot(selectedProduct.getLaborCostPerSquareFoot());
        newOrder.setCostPerSquareFoot(selectedProduct.getCostPerSquareFoot());

        service.calculateOrderCost(newOrder);
        if(view.getUserConfirmation(newOrder, "Would you like to confirm and place the order? (Y/N)")) {
            service.addOrder(newOrder, dateFromUser);
        }



    }

    private void editOrder() throws InvalidUserInputException, DataPersistanceException, OrderNotFoundException, InvalidTaxInformationException, InvalidOrderInformationException {
        view.displayEditOrderBanner();
        LocalDate userDate = view.getUserDateChoice();
        int userOrderId = view.getUserOrderIdChoice();
        Order orderFound = service.getOrder(userOrderId, userDate);
        boolean recalculate = false;
        boolean hasError = false;

        // edit customer name
        do {
            String newCustomerName = view.getUserCustomerNameChoiceEdit(orderFound);
            if(!newCustomerName.isBlank()) {
                try {
                    service.validateCustomerName(orderFound, newCustomerName);
                    hasError = false;
                } catch (InvalidOrderInformationException e) {
                    view.displayErrorMessage(e.getMessage());
                    hasError = true;
                }
            } else {
                break;
            }

        } while(hasError);

        // reset to false in case user enter invalid input, then decide to change nothing by pressing enter
        hasError = false;

        do {
            // edit state
            String newState = view.getUserStateNameChoiceEdit(orderFound);
            if(!newState.isBlank()) {
                try {
                    service.validateState(orderFound, newState);
                    recalculate = true;
                    hasError = false;
                } catch (InvalidTaxInformationException e) {
                    view.displayErrorMessage(e.getMessage());
                    hasError = true;
                }
            }
        } while(hasError);

        // reset to false in case user enter invalid input, then decide to change nothing by pressing enter
        hasError = false;

        // edit product type
        List<Product> productsList = service.getAllProduct();
        String productOptionFromUser = view.getUserProductTypeByNumberChoiceEdit(orderFound, productsList);
        if(!productOptionFromUser.isBlank()) {
            int productIndex = Integer.parseInt(productOptionFromUser) - 1;
            Product productSelected = productsList.get(productIndex);
            orderFound.setProductType(productSelected.getProductType());
            orderFound.setCostPerSquareFoot(productSelected.getCostPerSquareFoot());
            orderFound.setLaborCostPerSquareFoot(productSelected.getLaborCostPerSquareFoot());
            recalculate = true;
        }

        do {
            // edit area size
            String area = view.getUserAreaChoiceEdit(orderFound);
            if(!area.isBlank()) {
                try {
                    service.validateArea(orderFound, area);
                    recalculate = true;
                    hasError = false;
                } catch (InvalidOrderInformationException e) {
                    view.displayErrorMessage(e.getMessage());
                    hasError = true;
                } catch (NumberFormatException e) {
                    view.displayErrorMessage("ERROR: area must be a number.");
                    hasError = true;
                }
            }
        } while(hasError);


        if(recalculate) {
            service.calculateOrderCost(orderFound);
        }

        if(view.getUserConfirmation(orderFound, "Would you like to confirm and save the order? (Y/N)")) {
            service.editOrder(userDate, orderFound);
        }

    }

    private void removeOrder() throws InvalidUserInputException, OrderNotFoundException, DataPersistanceException {
        view.displayRemoveOrderBanner();
        LocalDate userDate = view.getUserDateChoice();
        int userOrderId = view.getUserOrderIdChoice();
        Order orderFound = service.getOrder(userOrderId, userDate);

        if(view.getUserConfirmation(orderFound, "Would you like to confirm and remove the order? (Y/N)")) {
            service.removeOrder(orderFound.getOrderId(), userDate);
        }

    }

    private void exportAllData() throws DataPersistanceException {
        view.displayExportOrdersBanner();
        service.exportAll();
    }

    private void quit() {
        System.out.println("quit");

    }



}
