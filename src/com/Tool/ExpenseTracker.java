package com.Tool;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class ExpenseTracker {
	private static List<Transaction> transactions = new ArrayList<>();
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("Load data from file? (yes/no): ");
		if (scanner.nextLine().equalsIgnoreCase("yes")) {
			System.out.print("Enter file path: ");
			String path = scanner.nextLine();
			transactions.addAll(FileHandler.loadTransactionsFromFile(path));
		}

		boolean running = true;
		while (running) {
			System.out.println("\n1. Add Income\n2. Add Expense\n3. View Monthly Summary\n4. Exit");
			String choice = scanner.nextLine().trim();

			switch (choice) {
			case "1":
				addTransaction("income");
				break;
			case "2":
				addTransaction("expense");
				break;
			case "3":
				viewMonthlySummary();
				break;
			case "4":
				running = false;
				break;
			default:
				System.out.println("Invalid choice!");
			}
		}
	}

	private static void addTransaction(String type) {
		System.out.print("Enter category (e.g., salary, food, rent, travel): ");
		String category = scanner.nextLine().trim();
		System.out.print("Enter amount: ");
		double amount = Double.parseDouble(scanner.nextLine().trim());
		LocalDate date = LocalDate.now();
		transactions.add(new Transaction(type, category, amount, date));
		System.out.println("Transaction added successfully.");
	}

	private static void viewMonthlySummary() {
		Map<Month, List<Transaction>> monthlyMap = transactions.stream()
				.collect(Collectors.groupingBy(t -> t.getDate().getMonth()));

		for (Month month : monthlyMap.keySet()) {
			double income = monthlyMap.get(month).stream().filter(t -> t.getType().equals("income"))
					.mapToDouble(Transaction::getAmount).sum();

			double expense = monthlyMap.get(month).stream().filter(t -> t.getType().equals("expense"))
					.mapToDouble(Transaction::getAmount).sum();

			System.out.printf("%s: Income = %.2f, Expense = %.2f%n", month, income, expense);
		}
	}
}
