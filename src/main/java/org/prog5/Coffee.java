package org.prog5;

import java.util.HashMap;
import java.util.Map;

public class Coffee {
    private final String name;
    private final double price;
    private final Map<String, Integer> ingredients;

    public Coffee(String name, double price, Map<String, Integer> ingredients) {
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Map<String, Integer> getIngredients() {
        return new HashMap<>(ingredients);
    }
}