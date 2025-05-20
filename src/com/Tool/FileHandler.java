package com.Tool;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class FileHandler {

    public static List<Transaction> loadTransactionsFromFile(String fileName) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String type = parts[0].trim().toLowerCase();
                    String category = parts[1].trim();
                    double amount = Double.parseDouble(parts[2].trim());
                    LocalDate date = LocalDate.parse(parts[3].trim());
                    transactions.add(new Transaction(type, category, amount, date));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return transactions;
    }
}

