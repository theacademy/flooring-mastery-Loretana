package com.flooringorder.dao;

import com.flooringorder.model.Tax;

import java.math.BigDecimal;

public class TaxDaoStubFileImpl implements TaxDao {

    public Tax texasTax;
    public Tax kentuckyTax;

    public TaxDaoStubFileImpl() {
        kentuckyTax = new Tax("KY", "Kentucky", new BigDecimal("6.00"));
        texasTax = new Tax("TX", "Texas", new BigDecimal("4.45"));
    }

    @Override
    public Tax getTaxByNameAbbrev(String taxName) throws DataPersistanceException {
        if (taxName.equals(texasTax.getStateName())) {
            return texasTax;
        } else if(taxName.equals(kentuckyTax.getStateName())) {
            return kentuckyTax;
        } else {
            return null;
        }
    }
}
