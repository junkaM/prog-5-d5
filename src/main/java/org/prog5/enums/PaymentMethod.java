package org.prog5.enums;

public enum PaymentMethod {
    CASH,
    CARD,
    MOBILE;

    public boolean isValid() {
        // Simulate validation logic
        return true;
    }
}