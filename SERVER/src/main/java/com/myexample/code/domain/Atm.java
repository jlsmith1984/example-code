package com.myexample.code.domain;

public class Atm {

    int[][] inventory;
    int inventoryTotal;

    public Atm() {
        restockInventory();
    }

    public void restockInventory() {
        this.inventory = new int[][] {{1, 10},
            {5, 10},
            {10, 10},
            {20, 10},
            {100, 10}};
        this.inventoryTotal = calculateInventoryTotal();
    }

    public int calculateInventoryTotal() {
        int accumulator = 0;
        int[][] testInventory = this.inventory;
        for (int[] ints : testInventory) {
            accumulator += (ints[0] * ints[1]);
        }
        return accumulator;
    }

    public boolean isInsufficientFunds(int debitAmount) {
        return getInventoryTotal() < debitAmount;
    }

    public int calculateRemainingInventoryTotal(int[][] inventory, int length) {
        int accumulator = 0;
        for (int i = 0; i <= length; i++) {
            accumulator += (inventory[i][0] * inventory[i][1]);
        }
        return accumulator;
    }

    public int[][] getBillsToDispense(int debitAmount) {
        int[][] billsToDispense = new int[this.inventory.length][2];
        for (int i = this.inventory.length -1; i >= 0; i--) {
            if (isInsufficientFunds(debitAmount)) {
                return null;
            }
            int numberOfBills = debitAmount / this.inventory[i][0];
            numberOfBills = Math.min(numberOfBills, this.inventory[i][1]);
            int remainingInventoryTotal = calculateRemainingInventoryTotal(this.inventory, i);
            if (remainingInventoryTotal < debitAmount) {
                return null;
            }
            billsToDispense[i][0] = this.inventory[i][0];
            billsToDispense[i][1] = numberOfBills;
            int amountToDebit = numberOfBills * this.inventory[i][0];
            this.inventoryTotal -= amountToDebit;
            debitAmount -= amountToDebit;
        }
        return billsToDispense;
    }

    public void debitAtmAndDispenseBills(int[][] billsToDispense) {
        if (billsToDispense != null) {
            System.out.println("Dispensing:");
            for (int i = 0; i < this.inventory.length; i++) {
                this.inventory[i][1] = this.inventory[i][1] - billsToDispense[i][1];
                System.out.println(String.format("$%d,%d", billsToDispense[i][0], billsToDispense[i][1]));
            }
        }
    }

    public void showInventory() {
        System.out.println("Inventory: ");
        for (int[] ints : inventory) {
            System.out.println(String.format("$%d,%d", ints[0], ints[1]));
        }
    }

    public int[][] getInventory() {
        return inventory;
    }

    public int getInventoryTotal() {
        return inventoryTotal;
    }

    public void setInventoryTotal(int newInventoryTotal) {
        this.inventoryTotal = newInventoryTotal;
    }
}
