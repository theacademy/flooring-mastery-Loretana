package com.flooringorder.ui;

import com.flooringorder.model.Order;
import com.flooringorder.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class FlooringView {

    private UserIO io;

    @Autowired
    public FlooringView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        displayStars();
        io.print("* <<Flooring Program>>");
        io.print("* 1. Display Orders");
        io.print("* 2. Add an Order");
        io.print("* 3. Edit an Order");
        io.print("* 4. Remove an Order");
        io.print("* 5. Export All Data");
        io.print("* 6. Quit");
        displayStars();

        return io.readInt("Please select from the above choices.", 1, 6);
    }

    public void displayStars() {
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }

    public LocalDate getUserDateChoice() throws InvalidUserInputException {
        return io.readDate("Please enter a date using format YYYY-MM-DD: ");
    }

    public String getUserCustomerNameChoice() {
        return io.readString("Please enter a customer name:");
    }

    public String getUserStateChoice() {
        return io.readString("Please enter a state name abbreviation: ");
    }

    public String getUserAreaChoice() {
        return io.readString("Please enter a area size: ");
    }

    public int getUserProductTypeByNumberChoice(List<Product> products) {
        displayAllProductType(products);
        return io.readInt("Please enter the number related to a product: ", 1, products.size());
    }

    public void displayAllProductType(List<Product> products) {
        int count = 1;
        for(Product currentProduct : products) {
            io.print(count + " - " + currentProduct.getProductType()
                    + "\t\t\tCostPerSquareFoot: $" + currentProduct.getCostPerSquareFoot()
                    + "\tLaborCostPerSquareFoot: $" + currentProduct.getLaborCostPerSquareFoot());
            count++;
        }
    }

    public void displayAllOrders(List<Order> orders) {
        for(Order currentOrder : orders) {
            io.print(currentOrder.toString());
        }
    }

    public void displayBanner() {
        io.print("==== Display Order ====");
    }

    public void displayAddOrderBanner() {
        io.print("==== Add Order ====");
    }

    public void displayEditOrderBanner() {
        io.print("==== Edit Order ====");
    }

    public void displayRemoveOrderBanner() {
        io.print("==== Remove Order ====");
    }

    public void displayExportOrdersBanner() {
        io.print("==== Export Orders ====");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }

    public boolean getUserConfirmation(Order newOrder, String prompt) {
        String userConfirmation = "";
        io.print("Current Order: " + newOrder.toString());
        boolean isValid = false;
        while(!isValid) {
            userConfirmation = io.readString(prompt);
            if(userConfirmation.equalsIgnoreCase("n") || userConfirmation.equalsIgnoreCase("y")) {
                isValid = true;
            }
        }

        return (userConfirmation.equalsIgnoreCase("Y"));

    }

    public int getUserOrderIdChoice() {
        return io.readInt("Please enter a number order number: ");
    }

    public String getUserCustomerNameChoiceEdit(Order order) {
        io.print("Current customer name: " + order.getCustomerName());
        return io.readString("Enter a name to update or press Enter to keep the current value");
    }

    public String getUserStateNameChoiceEdit(Order order) {
        io.print("Current state: " + order.getState());
        return io.readString("Enter a state to update or press Enter to keep the current value");
    }

    public String getUserProductTypeByNumberChoiceEdit(Order order, List<Product> productList) {
        displayAllProductType(productList);
        String optionSelected = "";
        String numberOnlyRegex = "^[0-9]+$";
        boolean isValid = false;
        io.print("Current product: " + order.getProductType());
        while(!isValid) {
            optionSelected = io.readString("Enter the product number to update or press Enter to keep the current value.");
            if(optionSelected.matches(numberOnlyRegex)) {
                int productSelected = Integer.parseInt(optionSelected);
                if(productSelected >= 1 && productSelected <= productList.size()) {
                    isValid = true;
                }
            } else if(optionSelected.isBlank()) {
                isValid = true;
            }
        }
        return optionSelected;
    }

    public String getUserAreaChoiceEdit(Order order) {
        io.print("Current Area size: " + order.getArea().toString());
        return io.readString("Enter a area size to update or press Enter to keep the current value");
    }
}
