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
        return sc.nextInt();
    }

    @Override
    public LocalDate readDate(String prompt) throws InvalidUserInputException {
        print(prompt);
        String dateAsText = sc.nextLine();
        try {
            return LocalDate.parse(dateAsText, FORMATTER_ISO);
        } catch (DateTimeParseException e) {
            throw new InvalidUserInputException("ERROR: Invalid date format, must follow ISO-8601 format: YYYY-MM-DD", e);
        }
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        String input = "";
        int val = Integer.MAX_VALUE;
        while(val > max || val < min) {
            print(prompt);
            input = sc.nextLine();
            val = Integer.parseInt(input);
        }
        return val;
    }

}
