package com.flooringorder.dao;

import com.flooringorder.model.Tax;

public interface TaxDao {

    Tax getTaxByName(String taxName);

}
