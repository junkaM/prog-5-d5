package org.prog5;

import org.prog5.enums.PaymentMethod;

public class Main {
    public static void main(String[] args) {
        try {
            CoffeeMachine machine = new CoffeeMachine();
            User user = new User("USER001", PaymentMethod.CARD);

            Payment payment = user.pay(5.0);
            boolean paymentSuccess = machine.processPayment(payment, 3.5);
            if (!paymentSuccess) {
                System.out.println("Payment failed: " + payment.getStatus());
                return;
            }

            Coffee coffee = user.chooseCoffee(machine, "cappuccino");

            machine.prepareCoffee(coffee);
            user.receiveCoffee(machine);

            System.out.println("Successfully prepared and distributed " + coffee.getName());
            System.out.println("Remaining stock: " + machine.getStock().getIngredients());

        } catch (CoffeeMachineException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}