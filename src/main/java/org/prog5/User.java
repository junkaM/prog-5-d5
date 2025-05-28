package org.prog5;

import org.prog5.enums.PaymentMethod;

public class User {
    private final String id;
    private final PaymentMethod preferredPaymentMethod;

    public User(String id, PaymentMethod preferredPaymentMethod) {
        this.id = id;
        this.preferredPaymentMethod = preferredPaymentMethod;
    }

    public Payment pay(double amount) {
        return new Payment(amount, preferredPaymentMethod);
    }

    public Coffee chooseCoffee(CoffeeMachine machine, String coffeeName) throws CoffeeMachineException {
        return machine.selectCoffee(coffeeName);
    }

    public void receiveCoffee(CoffeeMachine machine) throws CoffeeMachineException {
        machine.distributeCoffee();
    }

    public String getId() {
        return id;
    }
}
