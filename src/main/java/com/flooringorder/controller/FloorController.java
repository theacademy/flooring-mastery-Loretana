package com.flooringorder.controller;

import com.flooringorder.service.FlooringService;
import com.flooringorder.ui.FlooringView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        try {
            while(keepGoing) {
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
            }
        } catch (UnsupportedOperationException e) {

        }
    }

    private void displayOrder() {
        System.out.println("displayOrder");
    }

    private void addOrder() {
        System.out.println("addOrder");

    }

    private void editOrder() {
        System.out.println("editOrder");

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
