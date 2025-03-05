package com.flooringorder.dao;

import com.flooringorder.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getAllProduct() throws DataPersistanceException;

    Product getProductByType(String productType) throws DataPersistanceException;


}
