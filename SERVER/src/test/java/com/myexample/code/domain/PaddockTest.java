package com.myexample.code.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class PaddockTest {

    Paddock paddock;
    int testRaceHorsesCount;
    String testWinningHorseNumber;
    String testWinningHorseName;

    @Before
    public void init() {
        this.paddock = new Paddock();
        this.testRaceHorsesCount = 7;
        this.testWinningHorseNumber = "1";
        this.testWinningHorseName = "That Darn Gray Cat";
    }

    @Test
    public void testShowPaddock() {
        paddock.showPaddock();
    }

    @Test
    public void testInitRacehorses_WhenInitRacehorses_ThenRaceHorsesArrayValuesEqualTestValues() {
        String[][] initialRaceHorses = paddock.initRaceHorses();
        assertEquals(testRaceHorsesCount, initialRaceHorses.length);
        assertEquals(testWinningHorseName, initialRaceHorses[0][0]);
    }

    @Test
    public void testInitRacehorses_WhenNewInstanceOfPaddock_ThenRaceHorsesCountEqualsTestRaceHorsesCount() {
        int raceHorsesCount = paddock.getRaceHorsesCount();
        assertEquals(testRaceHorsesCount, raceHorsesCount);
    }

    @Test
    public void testInitRacehorses_WhenNewInstanceOfPaddock_ThenWinningHorseNumberEqualsTestWinningHorseNumber() {
        String winningRaceHorseNumber = paddock.getCurrentWinningHorseNumber();
        assertEquals(testWinningHorseNumber, winningRaceHorseNumber);
    }

    @Test
    public void testInitRaceHorses_WhenNewInstanceOfPaddock_TheWinningHorseNameEqualsTestWinningHorseName() {
        String winningRaceHorseName = paddock.getCurrentWinningHorseName();
        assertEquals(testWinningHorseName, winningRaceHorseName);
    }

    @Test
    public void testAddRacehorse() {
        int newLength = paddock.getRaceHorsesCount() + 1;
        paddock.addRaceHorse("Seabiscuit");
        assertEquals(newLength, paddock.getRaceHorses().length);
        assertEquals("Seabiscuit", paddock.getRaceHorses()[newLength - 1][0]);
        System.out.println("New Racehorse name: " + paddock.getRaceHorses()[newLength - 1][0] + " odds: " + paddock.getRaceHorses()[newLength - 1][1]);
        System.out.println("Racehorse Count: " + paddock.getRaceHorsesCount());
        paddock.showPaddock();
    }

    @Test
    public void testDeleteRaceHorse_WhenRaceHorseNumberIsValid_ThenHorseIsDeleted() {
        System.out.println("Racehorse to delete name: " + paddock.getRaceHorses()[3][0]);
        paddock.showPaddock();
        paddock.addRaceHorse("Seabiscuit");
        int newLength = paddock.getRaceHorsesCount() - 1;
        paddock.deleteRaceHorse(8);
        assertEquals(newLength, paddock.getRaceHorses().length);
        System.out.println("Racehorse Count: " + paddock.getRaceHorsesCount());
        paddock.showPaddock();
    }

    @Test
    public void testDeleteRaceHorse_WhenRaceHorseNumberIsNotValid_ThenHorseIsNotDeleted() {
        int newLength = paddock.getRaceHorsesCount() - 1;
        paddock.showPaddock();
        paddock.deleteRaceHorse(12);
        assertNotEquals(newLength, paddock.getRaceHorses().length);
        System.out.println("Racehorse Count: " + paddock.getRaceHorsesCount());
        paddock.showPaddock();
    }
}
