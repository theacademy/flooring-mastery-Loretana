package com.flooringorder.dao;

import com.flooringorder.model.Tax;

public interface TaxDao {

    Tax getTaxByNameAbbrev(String taxName) throws DataPersistanceException;

}


