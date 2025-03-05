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
    private static final String PRODUCT_FILE_HEADER = "ProductType,CostPerSquareFoot,LaborCostPerSquareFoot";

    public ProductDaoFileImpl() {
        PRODUCT_FILE_PATH = "src/main/java/com/flooringorder/SampleFileData/Data/Products.txt";
    }

    public ProductDaoFileImpl(String PRODUCT_FILE) {
        this.PRODUCT_FILE_PATH = PRODUCT_FILE;
    }

    @Override
    public List<Product> getAllProduct() throws DataPersistanceException {
        loadProduct();
        return new ArrayList<>(productMap.values());
    }

    @Override
    public Product getProductByType(String productType) throws DataPersistanceException {
        loadProduct();
        return productMap.get(productType);
    }

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

    private void writeProduct() throws DataPersistanceException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(PRODUCT_FILE_PATH));
        } catch (IOException e) {
            throw new DataPersistanceException("Could not save product data.", e);
        }

        String productAsText;
        List<Product> productList = this.getAllProduct();
        // insert product header as the first line of the file
        out.println(PRODUCT_FILE_HEADER);

        for(Product currentProduct : productList) {
            productAsText = marshallProduct(currentProduct);
            out.println(productAsText);
            out.flush();
        }

        out.close();
    }

    /*
     * Convert a Product object into a String
     * */
    private String marshallProduct(Product product){
        String productAsText;

        productAsText = product.getProductType() + DELIMITER;
        productAsText += product.getCostPerSquareFoot().toString() + DELIMITER;
        productAsText += product.getLaborCostPerSquareFoot().toString();

        return productAsText;
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
