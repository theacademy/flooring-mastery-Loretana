package com.flooringorder.dao;

import com.flooringorder.model.Product;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
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

    /*
    * Return all existing product in Products.txt file.
    * */
    @Override
    public List<Product> getAllProduct() throws DataPersistanceException {
        loadProduct();
        return new ArrayList<>(productMap.values());
    }

    /*
    * Return a product obj by his type
    * */
    @Override
    public Product getProductByType(String productType) throws DataPersistanceException {
        loadProduct();
        return productMap.get(productType);
    }

    /*
    * Load all product from file into memory hashmap
    * */
    private void loadProduct() throws DataPersistanceException {
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE_PATH)));
        } catch (FileNotFoundException e) {
            throw new DataPersistanceException("Could Not load product data into memory", e);
        }

        String currentLine;
        Product currentProduct;

        // skip header line to avoid conflict in unmarshallProduct()
        if(scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while(scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentProduct = unmarshallProduct(currentLine);
            productMap.put(currentProduct.getProductType(), currentProduct);
        }

    }

    /*
     * Convert a String of Product into a Product Object
     * */
    private Product unmarshallProduct(String productAsText) {
        String[] productTokens = productAsText.split(DELIMITER);
        String productType = productTokens[0];
        BigDecimal costPerSquareFeet = new BigDecimal(productTokens[1]);
        BigDecimal laborCostPerSquareFoot = new BigDecimal(productTokens[2]);

        return new Product(productType, costPerSquareFeet, laborCostPerSquareFoot);
    }


}
