package com.flooringorder.dao;

import com.flooringorder.model.Product;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

@Component
public class ProductDaoFileImpl implements ProductDao {

    private HashMap<String, Product> productMap = new HashMap<>();
    private final String PRODUCT_FILE_PATH;
    private static final String DELIMITER = ",";

    public ProductDaoFileImpl() {
        PRODUCT_FILE_PATH = "src/main/java/com/flooringorder/SampleFileData/Data/Products.txt";
    }

    public ProductDaoFileImpl(String PRODUCT_FILE) {
        this.PRODUCT_FILE_PATH = PRODUCT_FILE;
    }

    @Override
    public List<Product> getAllProduct() {
        loadProduct();
        throw new UnsupportedOperationException();
    }

    @Override
    public Product getProductByType(String productType) {
        loadProduct();
        throw new UnsupportedOperationException();
    }

    private void loadProduct() {
        Scanner scanner;
        throw new UnsupportedOperationException();
    }

    private void writeProduct() {
        Scanner scanner;
        throw new UnsupportedOperationException();
    }

    /*
     * Convert a Product object into a String
     * */
    private String marshallProduct(Product product){
        String productAsText;
        throw new UnsupportedOperationException();
    }

    /*
     * Convert a String of Product into a Product Object
     * */
    private Product unmarshallProduct(String productAsText) {
        Product product;
        throw new UnsupportedOperationException();
    }


}
