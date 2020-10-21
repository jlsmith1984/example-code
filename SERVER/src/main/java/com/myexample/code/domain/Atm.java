package com.myexample.code.domain;

import java.util.Iterator;
import java.util.TreeSet;

public class Atm {

    int[][] inventory;
    int[][] currencyToDispense;
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
        for (int[] values : this.inventory) {
            accumulator += (values[0] * values[1]);
        }
        return accumulator;
    }

    public boolean isInsufficientFunds(int debitAmount) {
        return this.inventoryTotal < debitAmount;
    }

    public int calculateRemainingInventoryTotal(int[][] inventory, int length) {
        int accumulator = 0;
        for (int i = 0; i <= length; i++) {
            accumulator += (inventory[i][0] * inventory[i][1]);
        }
        return accumulator;
    }

    public boolean hasCurrencyToDispense(int debitAmount) {
        int[][] currencyToDispense = new int[this.inventory.length][2];
        for (int i = this.inventory.length -1; i >= 0; i--) {
            if (isInsufficientFunds(debitAmount)) {
                return false;
            }
            int denominationCount = debitAmount / this.inventory[i][0];
            denominationCount = Math.min(denominationCount, this.inventory[i][1]);
            int remainingInventoryTotal = calculateRemainingInventoryTotal(this.inventory, i);
            if (remainingInventoryTotal < debitAmount) {
                return false;
            }
            currencyToDispense[i][0] = this.inventory[i][0];
            currencyToDispense[i][1] = denominationCount;
            int amountToDebit = denominationCount * this.inventory[i][0];
            debitAmount -= amountToDebit;
        }
        this.currencyToDispense = currencyToDispense;
        return true;
    }

    public void debitAtmAndDispenseCurrency(int payout) {
        if (this.currencyToDispense != null) {
            System.out.println("Dispensing:");
            for (int i = 0; i < this.inventory.length; i++) {
                this.inventory[i][1] = this.inventory[i][1] - this.currencyToDispense[i][1];
                System.out.println(String.format("$%d,%d", this.currencyToDispense[i][0], this.currencyToDispense[i][1]));
            }
            this.inventoryTotal -= payout;
        }
    }

    public void showInventory() {
        System.out.println("Inventory: ");
        for (int[] values : inventory) {
            System.out.println(String.format("$%d,%d", values[0], values[1]));
        }
    }

    public void addCash(int denomination, int denominationCount) {

        switch (denomination) {
            case 1:
            case 5:
            case 10:
            case 20:
            case 50:
            case 100: {
                TreeSet<Currency> inv = new TreeSet<>(Currency.getDenominationComparator());
                int currentCurrencyCount = 0;
                for (int[] values : this.inventory) {
                    if (values[0] == denomination) {
                        currentCurrencyCount = values[1];
                        inv.remove(new Currency(values[0], values[1]));
                    } else {
                        inv.add(new Currency(values[0], values[1]));
                    }
                }
                Currency currency = new Currency(denomination, denominationCount + currentCurrencyCount);
                inv.add(currency);
                resizeAndPopulateInventory(inv);
                this.inventoryTotal = calculateInventoryTotal();
                break;
            }
            default:
                System.out.println("Invalid Denomination and Count: " + denomination + " " + denominationCount);
        }
    }

    public void removeCash(int denomination, int denominationCount) {

        switch (denomination) {
            case 1:
            case 5:
            case 10:
            case 20:
            case 50:
            case 100: {
                TreeSet<Currency> inv = new TreeSet<>(Currency.getDenominationComparator());
                for (int[] values : this.inventory) {
                    if (values[0] == denomination) {
                        inv.remove(new Currency(values[0], values[1]));
                        if (values[1] > denominationCount)
                            inv.add(new Currency(values[0], values[1] - denominationCount));
                    } else {
                        inv.add(new Currency(values[0], values[1]));
                    }
                }
                resizeAndPopulateInventory(inv);
                this.inventoryTotal = calculateInventoryTotal();
                break;
            }
            default:
                System.out.println("Invalid Denomination and Count: " + denomination + " " + denominationCount);
        }
    }

    private void resizeAndPopulateInventory(TreeSet<Currency> tmpInventory) {
        Iterator<Currency> currencyIterator = tmpInventory.iterator();
        int i = 0;
        this.inventory = new int[tmpInventory.size()][2];
        while (currencyIterator.hasNext()) {
            Currency currency = currencyIterator.next();
            this.inventory[i][0] = currency.getDenomination();
            this.inventory[i++][1] = currency.getCount();
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
