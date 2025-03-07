package com.flooringorder.dao;

import com.flooringorder.model.Product;

import java.util.List;

public interface ProductDao {

    /**
     * Return a list of all Product obj.
     *
     * @return a list of all Product obj
     * if it exists, empty list otherwise
     * @throws DataPersistanceException
     */
    List<Product> getAllProduct() throws DataPersistanceException;

    /**
     * Return a Product obj associated with the given productType.
     *
     * @param productType id with which type is to be associated
     * @return the product object associated with the given productType
     * if it exists, null otherwise
     * @throws DataPersistanceException
     */
    Product getProductByType(String productType) throws DataPersistanceException;

}
