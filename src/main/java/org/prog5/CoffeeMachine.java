package org.prog5;

import org.prog5.enums.MachineState;

import java.util.HashMap;
import java.util.Map;

public class CoffeeMachine {
    private MachineState state;
    private final Stock stock;
    private final Map<String, Coffee> coffeeMenu;

    public CoffeeMachine() {
        this.state = MachineState.READY;
        this.stock = new Stock();
        this.coffeeMenu = initializeCoffeeMenu();
    }

    private Map<String, Coffee> initializeCoffeeMenu() {
        Map<String, Coffee> menu = new HashMap<>();
        menu.put("espresso", new Coffee("Espresso", 2.5, Map.of("coffee", 10, "water", 50)));
        menu.put("cappuccino", new Coffee("Cappuccino", 3.5, Map.of("coffee", 10, "water", 50, "milk", 100)));
        menu.put("latte", new Coffee("Latte", 4.0, Map.of("coffee", 10, "water", 50, "milk", 150)));
        return menu;
    }

    public boolean processPayment(Payment payment, double amount) throws CoffeeMachineException {
        if (state != MachineState.READY) {
            throw new CoffeeMachineException("Machine is not ready");
        }
        return payment.validate(amount);
    }

    public Coffee selectCoffee(String coffeeName) throws CoffeeMachineException {
        if (state != MachineState.READY) {
            throw new CoffeeMachineException("Machine is not ready");
        }
        Coffee coffee = coffeeMenu.get(coffeeName.toLowerCase());
        if (coffee == null) {
            throw new CoffeeMachineException("Invalid coffee selection");
        }
        if (stock.checkAvailability(coffee.getIngredients())) {
            throw new CoffeeMachineException("Insufficient ingredients");
        }
        return coffee;
    }

    public void prepareCoffee(Coffee coffee) throws CoffeeMachineException {
        if (state != MachineState.READY) {
            throw new CoffeeMachineException("Machine is not ready");
        }
        state = MachineState.PREPARING;
        try {
            stock.reduceStock(coffee.getIngredients());
            Thread.sleep(2000);
            state = MachineState.READY;
        } catch (InterruptedException e) {
            state = MachineState.ERROR;
            throw new CoffeeMachineException("Preparation interrupted");
        }
    }

    public void distributeCoffee() throws CoffeeMachineException {
        if (state != MachineState.READY) {
            throw new CoffeeMachineException("Cannot distribute: machine not ready");
        }
        try {
            System.out.println("Distributing coffee...");
            Thread.sleep(1000);
            System.out.println("Coffee distributed successfully");
        } catch (InterruptedException e) {
            state = MachineState.ERROR;
            throw new CoffeeMachineException("Distribution interrupted");
        }
    }

    public MachineState getState() {
        return state;
    }

    public Stock getStock() {
        return stock;
    }

    public Map<String, Coffee> getCoffeeMenu() {
        return new HashMap<>(coffeeMenu);
    }
}