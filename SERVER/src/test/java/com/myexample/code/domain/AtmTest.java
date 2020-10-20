package com.myexample.code.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class AtmTest {

    Atm atm;
    int testInventoryTotal;
    int testInventoryTotalAfterPayout;
    int testDebitAmount;
    int testDebitAmountExpectedHundreds;
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
        testDebitAmountExpectedHundreds = 3;
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
        int[][] payout = atm.getBillsToDispense(testDebitAmount);
        assertNotNull(payout);     // payout
        int numHundreds = payout[payout.length -1][1];
        assertEquals(testDebitAmountExpectedHundreds, numHundreds);
        assertEquals(testInventoryTotalAfterPayout, atm.getInventoryTotal());
        System.out.println(String.format("Payout = $%d" , testDebitAmount));
        atm.debitAtmAndDispenseBills(payout);
    }

    @Test
    public void testDebitAtm_WhenTestBigPayout_ThenResultsEqualTestingValues() {
        int[][] payout = atm.getBillsToDispense(testBigPayout);
        assertNotNull(payout);     // payout
        int numHundreds = atm.getInventory()[atm.getInventory().length -1][1];
        assertEquals(testBigPayoutExpectedHundreds, numHundreds);
        assertEquals(testInventoryTotalAfterBigPayout, atm.getInventoryTotal());
        System.out.println(String.format("Payout = $%d" , testBigPayout));
        atm.debitAtmAndDispenseBills(payout);
    }

    @Test
    public void testDebitAtm_WhenTestTooBigPayout_ThenNoPayout() {
        int[][] nullPayout = atm.getBillsToDispense(testTooBigPayout);
        assertNull(nullPayout);     // no payout
        int numHundreds = atm.getInventory()[atm.getInventory().length -1][1];
        assertEquals(testExpectedHundreds, numHundreds);
        assertEquals(testInventoryTotal, atm.getInventoryTotal());
        atm.showInventory();
    }

    @Test
    public void testDebitAtm_WhenNotEnoughBills_ThenNoPayout() {
        int testDebit = 195;
        atm.debitAtmAndDispenseBills(buildTestDebitArray());
        atm.setInventoryTotal(atm.calculateInventoryTotal());
        int[][] nullPayout = atm.getBillsToDispense(testDebit);
        assertNull(nullPayout);     // no payout
        System.out.println("Insufficient Funds: " + testDebit);
        int numHundreds = atm.getInventory()[atm.getInventory().length -1][1];
        assertEquals(testExpectedHundreds, numHundreds);
        atm.debitAtmAndDispenseBills(nullPayout);
        atm.showInventory();
    }

    // sufficient funds tests - isInsufficientFunds()
    @Test
    public void testInsufficientFunds_WhenInventoryLessThanBillsToPayout_ThenIsInsufficientFundsIsTrue() {
        assertTrue(atm.isInsufficientFunds(testTooBigPayout));
        System.out.println("Insufficient Funds: " + testTooBigPayout);
        atm.showInventory();
    }

    public int[][] buildTestDebitArray() {
        return new int[][] {{1, 6},
            {5, 10},
            {10, 0},
            {20, 0},
            {100, 0}};
    }
}
