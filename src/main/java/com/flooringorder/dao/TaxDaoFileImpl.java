package com.flooringorder.dao;

import com.flooringorder.model.Tax;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

@Component
public class TaxDaoFileImpl implements TaxDao {

    private HashMap<String, Tax> taxMap = new HashMap<>();
    private final String TAX_FILE_PATH;
    private static final String DELIMITER = ",";

    public TaxDaoFileImpl() {
        TAX_FILE_PATH = "src/main/java/com/flooringorder/SampleFileData/Data/Taxes.txt";
    }

    public TaxDaoFileImpl(String TAX_FILE) {
        this.TAX_FILE_PATH = TAX_FILE;
    }


    @Override
    public Tax getTaxByName(String taxName) {
        loadTax();
        return null;
    }


    private void loadTax() {
        Scanner scanner;
        throw new UnsupportedOperationException();
    }

    private void writeTax() {
        Scanner scanner;
        throw new UnsupportedOperationException();
    }

    /*
    * Convert an Tax object into a String
    * */
    private String marshallTax(Tax tax){
        String taxAsText;
        throw new UnsupportedOperationException();
    }

    /*
    * Convert a String of Tax into a Tax Object
    * */
    private Tax unmarshallTax(String taxAsText) {
        Tax tax;
        throw new UnsupportedOperationException();
    }

}
