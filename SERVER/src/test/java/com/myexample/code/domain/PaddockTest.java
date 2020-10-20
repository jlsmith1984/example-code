package com.myexample.code.domain;

import static org.junit.Assert.assertEquals;

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
}
