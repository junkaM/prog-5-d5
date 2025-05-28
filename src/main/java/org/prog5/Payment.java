package org.prog5;


import org.prog5.enums.PaymentMethod;
import org.prog5.enums.PaymentStatus;

public class Payment {
    private final double amount;
    private final PaymentMethod method;
    private PaymentStatus status;

    public Payment(double amount, PaymentMethod method) {
        this.amount = amount;
        this.method = method;
        this.status = PaymentStatus.PENDING;
    }

    public boolean validate(double requiredAmount) {
        if (amount < requiredAmount) {
            status = PaymentStatus.INSUFFICIENT;
            return false;
        }
        if (!method.isValid()) {
            status = PaymentStatus.INVALID;
            return false;
        }
        status = PaymentStatus.VALID;
        return true;
    }

    public PaymentStatus getStatus() {
        return status;
    }
}