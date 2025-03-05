package com.flooringorder;

import com.flooringorder.controller.FloorController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.flooringorder");
        appContext.refresh();

        FloorController controller = appContext.getBean("floorController", FloorController.class);
        controller.run();
    }
}