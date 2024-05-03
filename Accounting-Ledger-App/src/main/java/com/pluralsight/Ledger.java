package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Ledger {
    // Create a scanner to read user input
    public static Scanner input = new Scanner(System.in);
    // Create an array list to store transactions
    public static ArrayList<Transaction> transactions = new ArrayList<>();
    //Public static LocalDate date = LocalDate.now();
    //public static LocalTime time = LocalTime.now();
    public static void main(String[] args) {
        boolean running = true;
        while (running) {

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
            running = false;
            closeApplication();
        } else {
            System.out.println("Invalid selection");
        }
        }
        input.close();


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
        logger(date + "|" + time + "|" + description + "|" + vendor + "|" + String.format("%.2f",amount));
    }

    public static void payment() {
        System.out.println("Please enter the amount of the payment: ");
        double amount = input.nextDouble();
        input.nextLine();
        System.out.println("Please enter a description for your payment: ");
        String description = input.nextLine();
        System.out.println("Please enter the vendor you wish to pay: ");
        String vendor = input.nextLine();
        String date = getCurrentDate();
        String time = getCurrentTime();
        Transaction transaction = new Transaction(date, time, description, vendor, amount);
        transactions.add(transaction);
        System.out.println("Your transaction has been added");
        logger(date + "|" + time + "|" + description + "|" + vendor + "|" + String.format("%.2f",amount));
    }

    public static void ledger() {
        System.out.println("What would you like to do?: ");
        System.out.println("1: View all transactions");
        System.out.println("2: View all deposits");
        System.out.println("3: View all payments");
        System.out.println("4: View reports");
        System.out.println("5: Return to Homepage");
        int choice = input.nextInt();
        try (BufferedReader br = new BufferedReader(new FileReader("logs.csv"))) {
            String line;
            while ((line = br.readLine())!= null) {
                String[] data = line.split(" ");
                if (data.length >= 5) {
                String date = data[0];
                String time = data[1];
                String description = data[2];
                String vendor = data[3];
                double amount = Double.parseDouble(data[4]);
                Transaction transaction = new Transaction(date, time, description, vendor, amount);
                transactions.add(transaction);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
                int reportChoice = input.nextInt();

                //Calendar calendar = Calendar.getInstance();
                switch (reportChoice) {
                    case 1:
                        System.out.println("Month to Date");
                        LocalDate currentDate = LocalDate.parse(transaction.getDate());
                        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
                        if (currentDate.isAfter(firstDayOfMonth)) {
                            transactions.forEach(System.out::println);
                        }
                        break;
                    case 2:
                        System.out.println("Previous Month");
                        LocalDate currentMonth = LocalDate.parse(transaction.getDate());
                        LocalDate previousMonth = currentMonth.minusMonths(1);
                        if (previousMonth.isBefore(currentMonth)) {
                            transactions.forEach(System.out::println);
                        }
                        break;
                    case 3:
                        System.out.println("Year to Date");
                        LocalDate yearToDate = LocalDate.parse(transaction.getDate());
                        LocalDate firstDayOfYear = yearToDate.withDayOfYear(1);
                        if (yearToDate.isAfter(firstDayOfYear)) {
                            transactions.forEach(System.out::println);
                        }
                        break;
                    case 4:
                        System.out.println("Previous Year");
                        LocalDate currentYear = LocalDate.parse(transaction.getDate());
                        LocalDate previousYear = currentYear.minusYears(1);
                        if (previousYear.isBefore(currentYear)) {
                            transactions.forEach(System.out::println);
                        }
                        break;
                    case 5:
                        System.out.println("Search by vendor");
                        System.out.println("Please enter the vendor name: ");
                        String vendor = input.nextLine();
                            if (transaction.getVendor().equals(vendor)) {
                                System.out.println(transaction);
                            }
                        break;
                    case 6:
                        System.out.println("Go Back");
                        ledger();
                        break;
                }
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

    public static void closeApplication() {
        System.out.println("Thank you for using the Ledger");
        exitApplication();
    }



}
