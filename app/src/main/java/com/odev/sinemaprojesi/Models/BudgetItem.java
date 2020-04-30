package com.odev.sinemaprojesi.Models;

public class BudgetItem {
    String operation;
    int amount;

    public BudgetItem(String operation, int amount) {
        this.operation = operation;
        this.amount = amount;
    }

    public String getOperation() {
        return operation;
    }

    @Override
    public String toString() {
        return "BudgetItem{" +
                "operation='" + operation + '\'' +
                ", amount=" + amount +
                '}';
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
