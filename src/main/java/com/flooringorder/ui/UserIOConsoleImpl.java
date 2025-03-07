package com.flooringorder.ui;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

@Component
public class UserIOConsoleImpl implements UserIO {


    public Scanner sc = new Scanner(System.in);
    private final static DateTimeFormatter FORMATTER_ISO = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readString(String prompt) {
        print(prompt);
        return sc.nextLine();
    }

    @Override
    public int readInt(String prompt) {
        print(prompt);
        String numAsText = sc.nextLine();
        return Integer.parseInt(numAsText);
    }

    @Override
    public LocalDate readDate(String prompt) throws InvalidUserInputException {
        String dateAsText = "";
        String isoDateRegex = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
        while(!dateAsText.matches(isoDateRegex)) {
            print(prompt);
            dateAsText = sc.nextLine();
        }
        try {
            return LocalDate.parse(dateAsText, FORMATTER_ISO);
        } catch (DateTimeParseException e) {
            throw new InvalidUserInputException("ERROR: Invalid date", e);
        }
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        String input = "";
        String numberOnlyRegex = "^[0-9]+$";
        int val = Integer.MAX_VALUE;
        while(val > max || val < min) {
            print(prompt);
            input = sc.nextLine();
            if(input.matches(numberOnlyRegex)) {
                val = Integer.parseInt(input);
            }
        }
        return val;
    }

}
