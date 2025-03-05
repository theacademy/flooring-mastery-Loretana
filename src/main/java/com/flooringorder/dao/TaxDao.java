package com.flooringorder.dao;

import com.flooringorder.model.Tax;

public interface TaxDao {

    Tax getTaxByAbbrevName(String taxName) throws DataPersistanceException;

}
