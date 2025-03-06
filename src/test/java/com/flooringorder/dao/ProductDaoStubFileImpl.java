package com.flooringorder.dao;

import com.flooringorder.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoStubFileImpl implements ProductDao {

    public Product productA;
    public Product productB;

    public ProductDaoStubFileImpl() {
        productA = new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75"));
        productB = new Product("Tile", new BigDecimal("3.50"), new BigDecimal("4.15"));
    }

    @Override
    public List<Product> getAllProduct() throws DataPersistanceException {
        List<Product> productList = new ArrayList<>();
        productList.add(productA);
        productList.add(productB);
        return productList;
    }

    @Override
    public Product getProductByType(String productType) throws DataPersistanceException {
        if (productType.equals(productA.getProductType())) {
            return productA;
        } else if(productType.equals(productB.getProductType())) {
            return productB;
        } else {
            return null;
        }
    }
}
