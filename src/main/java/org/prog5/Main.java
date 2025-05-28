package org.prog5;

import org.prog5.enums.PaymentMethod;

import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Coffee selectedCoffee = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeMachine machine = new CoffeeMachine();
        User user = new User("USER001", PaymentMethod.CARD);

        while (true) {
            try {
                System.out.println("\n=== Coffee Machine Menu ===");
                System.out.println("1. View coffee menu");
                System.out.println("2. Check stock");
                System.out.println("3. Exit");
                System.out.print("Choose an option (1-3): ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> viewCoffeeMenu(scanner, machine, user);
                    case 2 -> displayStock(machine.getStock());
                    case 3 -> {
                        System.out.println("Thank you for using the coffee machine!");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("Invalid option. Please choose 1-3.");
                }
            } catch (CoffeeMachineException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }

    private static void viewCoffeeMenu(Scanner scanner, CoffeeMachine machine, User user)
            throws CoffeeMachineException {
        while (true) {
            displayCoffeeMenu(machine.getCoffeeMenu());
            System.out.println("\n=== Coffee Selection Menu ===");
            System.out.println("1. Select coffee");
            System.out.println("2. Exit to main menu");
            System.out.print("Choose an option (1-2): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    selectedCoffee = selectCoffee(scanner, machine, user);
                    makePayment(scanner, machine, user);
                    return;
                }
                case 2 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Please choose 1-2.");
            }
        }
    }

    private static void displayCoffeeMenu(Map<String, Coffee> coffeeMenu) {
        System.out.println("\nAvailable Coffees:");
        for (Map.Entry<String, Coffee> entry : coffeeMenu.entrySet()) {
            Coffee coffee = entry.getValue();
            System.out.printf("- %s: $%.2f%n", coffee.getName(), coffee.getPrice());
        }
    }

    private static Coffee selectCoffee(Scanner scanner, CoffeeMachine machine, User user)
            throws CoffeeMachineException {
        System.out.print("\nEnter coffee name (e.g., espresso, cappuccino, latte): ");
        String coffeeName = scanner.nextLine();
        Coffee coffee = user.chooseCoffee(machine, coffeeName);
        System.out.println("Selected: " + coffee.getName());
        return coffee;
    }

    private static void makePayment(Scanner scanner, CoffeeMachine machine, User user)
            throws CoffeeMachineException {
        if (selectedCoffee == null) {
            throw new CoffeeMachineException("No coffee selected.");
        }

        System.out.println("\n=== Payment Menu ===");
        System.out.println("1. Make payment");
        System.out.print("Choose an option (1): ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice != 1) {
            System.out.println("Invalid option. Payment required to proceed.");
            return;
        }

        System.out.println("\nPayment Methods:");
        System.out.println("1. Cash");
        System.out.println("2. Card");
        System.out.println("3. Mobile");
        System.out.print("Choose payment method (1-3): ");
        int methodChoice = scanner.nextInt();
        scanner.nextLine();

        PaymentMethod method = switch (methodChoice) {
            case 1 -> PaymentMethod.CASH;
            case 2 -> PaymentMethod.CARD;
            case 3 -> PaymentMethod.MOBILE;
            default -> throw new CoffeeMachineException("Invalid payment method");
        };

        System.out.printf("Enter payment amount (required: $%.2f): ", selectedCoffee.getPrice());
        double amount = scanner.nextDouble();
        scanner.nextLine();

        Payment payment = new Payment(amount, method);
        boolean success = machine.processPayment(payment, selectedCoffee.getPrice());
        if (success) {
            System.out.println("Payment successful: " + payment.getStatus());
            prepareAndDistributeCoffee(machine, user);
        } else {
            throw new CoffeeMachineException("Payment failed: " + payment.getStatus());
        }
    }

    private static void prepareAndDistributeCoffee(CoffeeMachine machine, User user)
            throws CoffeeMachineException {
        if (selectedCoffee == null) {
            throw new CoffeeMachineException("No coffee selected.");
        }
        machine.prepareCoffee(selectedCoffee);
        machine.distributeCoffee();
        selectedCoffee = null;
    }

    private static void displayStock(Stock stock) {
        System.out.println("\nCurrent Stock:");
        for (Map.Entry<String, Integer> entry : stock.getIngredients().entrySet()) {
            System.out.printf("- %s: %d units%n", entry.getKey(), entry.getValue());
        }
    }
}