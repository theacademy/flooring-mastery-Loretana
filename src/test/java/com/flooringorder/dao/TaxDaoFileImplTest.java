package com.flooringorder.dao;

import com.flooringorder.model.Tax;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaxDaoFileImplTest {

    TaxDaoFileImpl testTaxDao;
    public final static String TEST_TAX_FILE = "src/test/resources/Test/Data/TaxesTest.txt";

    @BeforeEach
    public void setUp() throws IOException {
        testTaxDao = new TaxDaoFileImpl(TEST_TAX_FILE);
    }

    @Test
    void getTaxByNameAbbrev() throws DataPersistanceException {
        Tax taxSelected = testTaxDao.getTaxByNameAbbrev("TX");
        assertEquals("Texas", taxSelected.getStateName(), "State name should be Texas");

        String calforniaName = "Calfornia";
        String calforniaAbbrev = "CA";
        BigDecimal taxRate = new BigDecimal("25.00");

        taxSelected = testTaxDao.getTaxByNameAbbrev(calforniaAbbrev);
        assertEquals(calforniaName, taxSelected.getStateName(), "State name should be Calfornia");
        assertEquals(calforniaAbbrev, taxSelected.getStateAbbreviation(), "State abbreviation should be CA");
        assertEquals(taxRate, taxSelected.getTaxRate(), "State tax rate should be 25.00");
    }

    @Test
    void getAllTax() throws DataPersistanceException {
        List<Tax> taxesList = testTaxDao.getAllTax();
        assertNotNull(taxesList, "taxesList should not be null");
        assertEquals(4, taxesList.size(), "Tax file should contains 4 type of tax");
    }
}