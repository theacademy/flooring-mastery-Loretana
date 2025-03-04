package com.flooringorder.ui;

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

        return io.readInt("Please select from the above choices.", 1, 5);
    }

    public void displayStars() {
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }

    public String getUserDateChoice() {
        return io.readString("Please enter a date using format YYYY-MM-DD: ");
    }

    public String getUserCustomerNameChoice() {
        return io.readString("Please enter a customer name:");
    }

    public String getUserStateChoice() {
        return io.readString("Please enter a state: ");
    }

    public String getUserAreaChoice() {
        return io.readString("Please enter a area size: ");
    }

    public int getUserProductTypeByNumberChoice(List<Product> products) {
        displayAllProductType(products);
        return io.readInt("Please enter the number related to a product: ");
    }

    public void displayAllProductType(List<Product> products) {
        int count = 1;
        for(Product currentProduct : products) {
            io.print(count + " - " + currentProduct.getProductType());
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





}
