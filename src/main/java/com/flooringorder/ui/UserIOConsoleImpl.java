package com.flooringorder.ui;

import java.util.Scanner;

public class UserIOConsoleImpl implements UserIO {


    public Scanner sc = new Scanner(System.in);

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
