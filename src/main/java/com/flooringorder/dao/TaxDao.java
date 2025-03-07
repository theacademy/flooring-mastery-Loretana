package com.flooringorder.dao;

import com.flooringorder.model.Tax;

public interface TaxDao {

    /**
     * Return a Tax obj associated with the given taxName.
     *
     * @param taxNameAbbrev id with which student is to be associated
     * @return the tax object associated with the given taxName
     * if it exists, null otherwise
     * @throws DataPersistanceException
     */
    Tax getTaxByNameAbbrev(String taxNameAbbrev) throws DataPersistanceException;

}


