package com.myexample.code.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class AtmTest {

    Atm atm;
    int testInventoryTotal;
    int testInventoryTotalAfterPayout;
    int testDebitAmount;
    int testBigPayout;
    int testTooBigPayout;
    int testBigPayoutExpectedHundreds;
    int testInventoryTotalAfterBigPayout;
    int testExpectedHundreds;

    @Before
    public void init() {
        atm = new Atm();
        testInventoryTotal = 1360;
        testDebitAmount = 333;
        testBigPayout = 1139;
        testTooBigPayout = 3161;
        testBigPayoutExpectedHundreds = 10;
        testInventoryTotalAfterBigPayout = 221;
        testInventoryTotalAfterPayout = testInventoryTotal - testDebitAmount;
        testExpectedHundreds = 10;
    }

    @Test
    public void testShowInventory() {
        atm.showInventory();
        System.out.println("Inventory Total: " + atm.getInventoryTotal());
    }

    @Test
    public void testAtmInventoryTotal_WhenNewAtm_ThenInventoryTotalEqualsTestInventoryTotal() {
        assertEquals(testInventoryTotal, atm.getInventoryTotal());
    }

    @Test
    public void testAtmRestockInventory_WhenAtmRestocked_ThenAmountEqualsRestockedAtmTotal() {
        atm.restockInventory();
        assertEquals(testInventoryTotal, atm.getInventoryTotal());
        System.out.println("Atm Cash on Hand: " + atm.getInventoryTotal());
    }

    @Test
    public void testCalculateInventoryTotal_WhenNewAtmInstance_ThenAmountEqualsTestInventoryTotal() {
        int accumulator = atm.calculateInventoryTotal();
        assertEquals(testInventoryTotal, accumulator);
        System.out.println("Atm Cash on Hand: " + accumulator);
    }

    @Test
    public void testDebitAtm_WhenTestDebitAmount_ThenResultsEqualTestingValues() {
        boolean hasPayout = atm.hasCurrencyToDispense(testDebitAmount);
        assertTrue(hasPayout);     // payout
        System.out.println(String.format("Payout = $%d" , testDebitAmount));
        atm.debitAtmAndDispenseCurrency(testDebitAmount);
        assertEquals(testInventoryTotalAfterPayout, atm.getInventoryTotal());
    }

    @Test
    public void testDebitAtm_WhenTestBigPayout_ThenResultsEqualTestingValues() {
        boolean hasPayout = atm.hasCurrencyToDispense(testBigPayout);
        assertTrue(hasPayout);     // payout
        int numHundreds = atm.getInventory()[atm.getInventory().length -1][1];
        System.out.println(String.format("Payout = $%d" , testBigPayout));
        atm.debitAtmAndDispenseCurrency(testBigPayout);
        assertEquals(testBigPayoutExpectedHundreds, numHundreds);
        assertEquals(testInventoryTotalAfterBigPayout, atm.getInventoryTotal());
    }

    @Test
    public void testDebitAtm_WhenTestTooBigPayout_ThenNoPayout() {
        boolean hasPayout = atm.hasCurrencyToDispense(testTooBigPayout);
        assertFalse(hasPayout);     // no payout
        int numHundreds = atm.getInventory()[atm.getInventory().length -1][1];
        assertEquals(testExpectedHundreds, numHundreds);
        assertEquals(testInventoryTotal, atm.getInventoryTotal());
        atm.showInventory();
    }

    @Test
    public void testDebitAtm_WhenNotEnoughCurrency_ThenNoPayout() {
        int testDebit = 195;
        buildTestDebitArray();
        atm.setInventoryTotal(atm.calculateInventoryTotal());
        boolean hasPayout = atm.hasCurrencyToDispense(testDebit);
        assertFalse(hasPayout);    // no payout
        System.out.println("Insufficient Funds: " + testDebit);
        int numHundreds = atm.getInventory()[atm.getInventory().length -1][1];
        atm.debitAtmAndDispenseCurrency(testDebit);
        assertEquals(testExpectedHundreds, numHundreds);
        atm.showInventory();
    }

    // sufficient funds tests - isInsufficientFunds()
    @Test
    public void testInsufficientFunds_WhenInventoryLessThanCurrencyToPayout_ThenIsInsufficientFundsIsTrue() {
        assertTrue(atm.isInsufficientFunds(testTooBigPayout));
        System.out.println("Insufficient Funds: " + testTooBigPayout);
        atm.showInventory();
    }

    @Test
    public void testAddCash() {
        int testLength = atm.getInventory().length;
        atm.addCash(50, 10);
        assertEquals(testLength + 1, atm.getInventory().length);
        atm.showInventory();
    }

    @Test
    public void testAddCash_InvalidDenomination_ThenCashNotAdded() {
        int testLength = atm.getInventory().length;
        atm.addCash(30 ,10);
        assertEquals(testLength, atm.getInventory().length);
        atm.showInventory();
    }

    @Test
    public void testRemoveCash() {
        int testLength = atm.getInventory().length;
        atm.removeCash(20 ,10);
        assertEquals(testLength - 1, atm.getInventory().length);
        atm.showInventory();
    }

    public void buildTestDebitArray() {
        atm.inventory = new int[][] {{1, 6},
            {5, 5},
            {10, 0},
            {20, 0},
            {100, 10}};
    }
}
