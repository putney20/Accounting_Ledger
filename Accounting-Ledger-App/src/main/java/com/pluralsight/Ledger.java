package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Ledger {
    // Create a scanner to read user input
    public static Scanner input = new Scanner(System.in);
    // Create an array list to store transactions
    public static ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    //blic static LocalDate date = LocalDate.now();
    //public static LocalTime time = LocalTime.now();
    public static void main(String[] args) {
        // Create a homepage for the Ledger
        System.out.println("Hello please make a selection: ");
        System.out.println("1: Make a deposit");
        System.out.println("2: Make a payment");
        System.out.println("3: Navigate to Ledger");
        System.out.println("4: Quit");

        // Create a scanner to read user input
        int choice = input.nextInt();

        if (choice == 1) {
            // Create a deposit method
            deposit();
        } else if (choice == 2) {
            // Create a payment method
            payment();
        } else if (choice == 3) {
            // Create a ledger method
            ledger();
        } else if (choice == 4) {
            //exitApplication();
        } else {
            System.out.println("Invalid selection");
        }


    }
    public static void exitApplication() {
        System.exit(0);
    }
    public static void deposit() {
        System.out.println("Please enter the amount of the deposit: ");
        double amount = input.nextDouble();
        input.nextLine();
        System.out.println("Please enter a description for your deposit: ");
        String description = input.nextLine();
        System.out.println("Please enter the vendor from wish to deposit: ");
        String vendor = input.nextLine();
        String date = getCurrentDate();
        String time = getCurrentTime();
        Transaction transaction = new Transaction(date, time, description, vendor, amount);
        transactions.add(transaction);
        System.out.println("Your transaction has been added");
        logger("Deposit: " + date + " " + time + " " + description + " " + vendor + " " + amount);
    }

    public static void payment() {
        System.out.println("Please enter the amount of the payment: ");
        double amount = input.nextDouble();
        System.out.println("Please enter a description for your payment: ");
        String description = input.nextLine();
        System.out.println("Please enter the vendor you wish to pay: ");
        String vendor = input.nextLine();
        String date = getCurrentDate();
        String time = getCurrentTime();
        Transaction transaction = new Transaction(date, time, description, vendor, amount);
        transactions.add(transaction);
        System.out.println("Your transaction has been added");
        logger("Payment: " + date + " " + time + " " + description + " " + vendor + " " + amount);
    }

    public static void ledger() {
        System.out.println("What would you like to do?: ");
        System.out.println("1: View all transactions");
        System.out.println("2: View all deposits");
        System.out.println("3: View all payments");
        System.out.println("4: View reports");
        System.out.println("5: Return to Homepage");
        int choice = input.nextInt();
        for (Transaction transaction : transactions) {
            if (choice == 1) {
            System.out.println(transaction);
            } else if (choice == 2) {
                double deposit = transaction.getAmount();
                if (deposit > 0) {
                    System.out.println(transaction);
                }
            } else if (choice == 3) {
                double payment = transaction.getAmount();
                if (payment < 0) {
                    System.out.println(transaction);
                }
            }   else if (choice == 4) {
                System.out.println("Please select a report type or search by vendor name: ");
                System.out.println("1: Month to Date");
                System.out.println("2: Previous Month");
                System.out.println("3: Year to Date");
                System.out.println("4: Previous Year");
                System.out.println("5: Search by vendor");
                System.out.println("6: Go Back");
            }
        }
    }

    public static String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now();
        //System.out.println(dtf.format(localDate));
        return dtf.format(localDate);
    }

    public static String getCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.now();
        return dtf.format(localTime);
    }

    public static void logger(String action){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("logs.csv", true));
            writer.write(action);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
