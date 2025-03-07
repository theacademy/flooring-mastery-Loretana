package com.flooringorder.ui;

import java.time.LocalDate;

public interface UserIO {

    void print(String msg);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    String readString(String prompt);

    LocalDate readDate(String prompt) throws InvalidUserInputException;

}
