package com.flooringorder.dao;

import com.flooringorder.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoFileImplTest {

    ProductDaoFileImpl testProductDao;
    public final static String TEST_PRODUCT_FILE = "src/main/java/com/flooringorder/test/Data/ProductsTest.txt";

    @BeforeEach
    public void setUp() throws IOException {
        testProductDao = new ProductDaoFileImpl(TEST_PRODUCT_FILE);
    }

    @Test
    void getAllProduct() throws DataPersistanceException {
        List<Product> productsList = testProductDao.getAllProduct();
        assertNotNull(productsList);
        assertEquals(4, productsList.size(), "List should contains 4 product type");
    }

    @Test
    void getProductByType() throws DataPersistanceException {
        Product woodClone = new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75"));
        Product selectedProduct = testProductDao.getProductByType("Wood");

        assertEquals(woodClone.getProductType(), selectedProduct.getProductType(), "Product type should be wood");
        assertEquals(woodClone.getCostPerSquareFoot(), selectedProduct.getCostPerSquareFoot(), "CostPerSquareFoot should be the same.");
        assertEquals(woodClone.getLaborCostPerSquareFoot(), selectedProduct.getLaborCostPerSquareFoot(), "LaborCostPerSquareFoot should be the same.");


    }
}