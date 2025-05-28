package org.prog5;


import java.util.HashMap;
import java.util.Map;

public class Stock {
    private final Map<String, Integer> ingredients;

    public Stock() {
        this.ingredients = new HashMap<>();

        ingredients.put("coffee", 1000);
        ingredients.put("water", 5000);
        ingredients.put("milk", 2000);
        ingredients.put("sugar", 500);
    }

    public boolean checkAvailability(Map<String, Integer> requiredIngredients) {
        for (Map.Entry<String, Integer> entry : requiredIngredients.entrySet()) {
            String ingredient = entry.getKey();
            int requiredQuantity = entry.getValue();
            if (!ingredients.containsKey(ingredient) || ingredients.get(ingredient) < requiredQuantity) {
                return true;
            }
        }
        return false;
    }

    public void reduceStock(Map<String, Integer> requiredIngredients) throws CoffeeMachineException {
        for (Map.Entry<String, Integer> entry : requiredIngredients.entrySet()) {
            String ingredient = entry.getKey();
            int requiredQuantity = entry.getValue();
            if (checkAvailability(Map.of(ingredient, requiredQuantity))) {
                throw new CoffeeMachineException("Insufficient " + ingredient);
            }
            ingredients.put(ingredient, ingredients.get(ingredient) - requiredQuantity);
        }
    }

    public Map<String, Integer> getIngredients() {
        return new HashMap<>(ingredients);
    }
}