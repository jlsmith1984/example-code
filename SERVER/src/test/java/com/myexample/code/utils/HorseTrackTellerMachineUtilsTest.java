package com.myexample.code.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.myexample.code.domain.Atm;
import com.myexample.code.domain.Paddock;
import org.junit.Before;
import org.junit.Test;

import static com.myexample.code.utils.HorseTrackTellerMachineUtils.*;

public class HorseTrackTellerMachineUtilsTest {
    Atm atm;
    Paddock paddock;
    String testWinningHorseNumber = "1";
    String testWager;
    int testWinningPayout;
    int testNumHundreds;
    String testTooBigPayout;
    String testWinningHorseName;

    @Before
    public void init() {
        atm = new Atm();
        paddock = new Paddock();
        testWager = "78";
        testWinningPayout = 390;
        testNumHundreds = 7;
        testTooBigPayout = "1456";
        testWinningHorseName = "That Darn Gray Cat";
    }

    // command tests for new bet command - isCommandNewBet()
    @Test
    public void testIsCommandNewBet_WhenCommandIsValidInt_ThenCommandIsNewBet() {
        String command = "1";
        assertTrue(isValidNewBetCommand(command));
        System.out.println(String.format("Command '%s' is a wager", command));
    }

    @Test
    public void testIsCommandNewBet_WhenCommandINotValidInt_ThenCommandIsNotNewBet() {
        String command = "z";
        assertFalse(isValidNewBetCommand(command));
        System.out.println(String.format("Command '%s' is not a wager", command));
    }

    // horse number tests - isValidHorseNumber()
    @Test
    public void testIsValidHorseNumber_WhenSelectedHorseNumberIsTwo_ThenHorseNumberIsValid() {
        boolean isValidHorseNumber = isValidHorseNumber(paddock.getRaceHorsesCount(), "2");
        assertTrue(isValidHorseNumber);
        System.out.println(String.format("Horse Number '%s' is valid", "2"));
    }

    @Test
    public void testIsValidHorseNumber_WhenSelectedHorseNumberIsZeroOrLess_ThenHorseNumberIsNotValid() {
        String raceHorseNumber = Integer.toString(0);
        boolean isValidHorseNumber = isValidHorseNumber(paddock.getRaceHorsesCount(), raceHorseNumber);
        assertFalse(isValidHorseNumber);
        System.out.println(String.format("Horse Number '%s' is not a valid horse", raceHorseNumber));
        raceHorseNumber = Integer.toString(paddock.getRaceHorsesCount() - (paddock.getRaceHorsesCount() + 1));
        isValidHorseNumber = isValidHorseNumber(paddock.getRaceHorsesCount(), raceHorseNumber);
        assertFalse(isValidHorseNumber);
        System.out.println(String.format("Horse Number '%s' is not a valid horse", raceHorseNumber));
    }

    @Test
    public void testIsValidHorseNumber_WhenSelectedHorseNumberIsGreaterThanNumberOfHorses_ThenHorseNumberIsNotValid() {
        String raceHorseNumber = Integer.toString(paddock.getRaceHorsesCount() + 1);
        boolean isValidHorseNumber = isValidHorseNumber(paddock.getRaceHorsesCount(), raceHorseNumber);
        assertFalse(isValidHorseNumber);
        System.out.println(String.format("Horse Number '%s' is not a valid horse", raceHorseNumber));
    }

    @Test
    public void testIsValidHorseNumber_WhenSelectedHorseNumberIsNotInteger_ThenHorseNumberIsNotValid() {
        boolean isValidHorseNumber = isValidHorseNumber(paddock.getRaceHorsesCount(), "someString");
        assertFalse(isValidHorseNumber);
        System.out.println(String.format("Horse Number '%s' is not a valid horse", "someString"));
    }

    @Test
    public void testIsValidHorseNumber_WhenSelectedHorseNumberIsFloat_ThenHorseNumberIsNotValid() {
        String horseNumber = Float.toString(2.1f);
        boolean isValidHorseNumber = isValidHorseNumber(paddock.getRaceHorsesCount(), horseNumber);
        assertFalse(isValidHorseNumber);
        System.out.println(String.format("Horse Number '%s' is not a valid horse", horseNumber));
    }

    // payout tests - noPayout(), payout()
    @Test
    public void testNoPayout_WhenNotWinningRaceHorse_ThenNoPayout() {
        String losingHorse = "4";
        noPayout(paddock, losingHorse);
    }

    @Test
    public void testPayout_WhenWagerIstestTooBigPayout_ThenThenNoPayout() {
        int payout = payout(atm, paddock, testTooBigPayout);
        assertEquals(0, payout);
        int numHundreds = atm.getInventory()[atm.getInventory().length -1][1];
        assertEquals(10, numHundreds);

    }

    @Test
    public void testPayout_WhenWagerIsTestWager_ThenPayoutEqualsTestWinningPayout() {
        int payout = payout(atm, paddock, testWager);
        assertEquals(testWinningPayout, payout);
        int numHundreds = atm.getInventory()[atm.getInventory().length -1][1];
        assertEquals(testNumHundreds, numHundreds);

    }

    @Test
    public void testPayout_WhenNotEnoughChange_ThenNoPayout() {
        paddock.setCurrentWinningHorseNumber("5");
        payout(atm, paddock, "3");
        payout(atm, paddock, "3");
        atm.showInventory();
        int[][] nullPayout = atm.getBillsToDispense(38);
        assertNull(nullPayout);     // no payout
        atm.showInventory();
    }

    @Test
    public void testCalculatePayout_WhenWagerIsTestWager_ThenPayoutEqualsTestWinningPayout() {
        int payout = calculatePayout(paddock, Integer.parseInt(paddock.getCurrentWinningHorseNumber()), testWager);
        assertEquals(testWinningPayout, payout);
        atm.showInventory();
    }

    @Test
    public void testDeterminePayout_ViewOutput() {
        determinePayout(atm, paddock, testWinningHorseNumber, testWager);
    }

    // valid bet tests for input - isValidBet()
    @Test
    public void testIsValidBet_WhenInputIsValidInt_ThenInputIsValidWager() {
        assertTrue(isValidBet(testWager));
        System.out.println(String.format("Input '%s' is a valid wager", testWager));
    }

    @Test
    public void testIsValidBet_WhenInputIsNotValidInt_ThenInputIsNotValidWager() {
        String input = "10.50";
        assertFalse(isValidBet(input));
        System.out.println(String.format("Input '%s' is not a valid wager", input));
    }

    @Test
    public void testIsValidBet_WhenInputBlank_ThenInputIsNotValidWager() {
        String input = "";
        assertFalse(isValidBet(input));
        System.out.println(String.format("Input '%s' is not a valid wager", input));
    }

    // get winning horse number tests - setNewWinningHorseNumber()
    @Test
    public void testSetNewWinningHorseNumber_WhenSelectedRaceHorseIsValid_ThenNewWinningHorseNumberIsUpdated() {
        String currentWinningHorseNumber = paddock.getCurrentWinningHorseNumber();
        String selectedWinningHorseNumber = "4";
        String newWinningHorseNumber = setNewWinningHorseNumber(paddock.getRaceHorsesCount(), currentWinningHorseNumber, selectedWinningHorseNumber);
        assertNotEquals(currentWinningHorseNumber, newWinningHorseNumber);
        assertEquals(selectedWinningHorseNumber, newWinningHorseNumber);
        System.out.println(String.format("New Winning Horse - name: '%s', number: '%s'",
            paddock.getRaceHorses()[Integer.parseInt(newWinningHorseNumber) - 1][0], newWinningHorseNumber));
    }

    @Test
    public void testSetNewWinningHorseNumber_WhenSelectedRaceHorseIsNotValid_ThenNewWinningHorseNumberIsNotUpdated() {
        String currentWinningHorseNumber = paddock.getCurrentWinningHorseNumber();
        String selectedWinningHorseNumber = "10";
        String newWinningHorseNumber = setNewWinningHorseNumber(paddock.getRaceHorsesCount(), currentWinningHorseNumber, selectedWinningHorseNumber);
        assertEquals(currentWinningHorseNumber, newWinningHorseNumber);
        assertNotEquals(selectedWinningHorseNumber, newWinningHorseNumber);
        System.out.println(String.format("Unchanged Winning Horse - name: '%s', number: '%s'",
            paddock.getRaceHorses()[Integer.parseInt(newWinningHorseNumber) - 1][0], newWinningHorseNumber));
    }

    @Test
    public void testSetNewWinningHorseNumber_WhenSelectedHorseIsEmpty_ThenNewWinningHorseNumberIsNotUpdated() {
        String currentWinningHorseNumber = paddock.getCurrentWinningHorseNumber();
        String selectedWinningHorseNumber = "";
        String newWinningHorseNumber = setNewWinningHorseNumber(paddock.getRaceHorsesCount(), currentWinningHorseNumber, selectedWinningHorseNumber);
        assertEquals(currentWinningHorseNumber, newWinningHorseNumber);
        assertNotEquals(selectedWinningHorseNumber, newWinningHorseNumber);
        System.out.println(String.format("Unchanged Winning Horse - name: '%s', number: '%s'",
            paddock.getRaceHorses()[Integer.parseInt(newWinningHorseNumber) - 1][0], newWinningHorseNumber));
    }

    // winning bet tests - isWinningBet()
    @Test
    public void testIsWinningBet_WhenSelectedHorseNumberEqualsWinningHorseNumber_ThenIsWinningBet() {
        String selectedHorseNumber = "1";
        assertTrue(isWinningBet(paddock.getCurrentWinningHorseNumber(), selectedHorseNumber));
        System.out.println(String.format("'%s' is a Winner!", paddock.getRaceHorses()[Integer.parseInt(selectedHorseNumber) - 1][0]));
    }

    @Test
    public void testIsWinningBet_WhenSelectedHorseNumberNotEqualWinningHorseNumber_ThenIsNotWinningBet() {
        String selectedHorseNumber = "7";
        assertFalse(isWinningBet(paddock.getCurrentWinningHorseNumber(), selectedHorseNumber));
        System.out.println(String.format("'%s' is not the Winner!", paddock.getRaceHorses()[Integer.parseInt(selectedHorseNumber) - 1][0]));
    }

    // valid command/input integer - isValidInt()
    @Test
    public void testIsValidInt_WhenStringContainsOnlyInteger_ThenIntegerIsValid() {
        String validInt = "123";
        boolean isValid = isValidInt(validInt);
        assertTrue(isValid);
        System.out.println(String.format("Integer '%s' is a valid int", validInt));
    }

    @Test
    public void testIsValidInt_WhenStringContainsAlphaNumeric_ThenIntegerIsNotValid() {
        String inValidInt = "123abc";
        boolean isValid = isValidInt(inValidInt);
        assertFalse(isValid);
        System.out.println(String.format("Integer '%s' is not a valid int", inValidInt));
    }
}
