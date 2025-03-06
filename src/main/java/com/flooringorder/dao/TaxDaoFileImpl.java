package com.flooringorder.dao;

import com.flooringorder.model.Tax;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public Tax getTaxByName(String taxName) throws DataPersistanceException {
        loadTax();
        return taxMap.get(taxName);
    }

    public List<Tax> getAllTax() throws DataPersistanceException {
        loadTax();
        return new ArrayList<>(taxMap.values());
    }

    private void loadTax() throws DataPersistanceException {
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(TAX_FILE_PATH)));
        } catch (FileNotFoundException e) {
            throw new DataPersistanceException("Could Not load tax data into memory.", e);
        }
        String currentLine;
        Tax currentTax;

        // skip header line to avoid conflict in unmarshallTax()
        if(scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while(scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTax = unmarshallTax(currentLine);
            taxMap.put(currentTax.getStateName(), currentTax);
        }

    }

    /*
    * Convert a String of Tax into a Tax Object
    * */
    private Tax unmarshallTax(String taxAsText) {
        String[] taxTokens = taxAsText.split(DELIMITER);
        String stateAbbreviation = taxTokens[0];
        String stateName = taxTokens[1];
        BigDecimal taxRate = new BigDecimal(taxTokens[2]).setScale(2, RoundingMode.HALF_UP);
        return new Tax(stateAbbreviation, stateName, taxRate);
    }

}
