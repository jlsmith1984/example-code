package com.myexample.code.domain;

import java.util.Random;

public class Paddock {

    private String[][] raceHorses;
    private int raceHorsesCount;
    private String currentWinningHorseNumber;
    private String currentWinningHorseName;

    public String[][] initRaceHorses() {
        // racehorse name, odds of winning
        return new String[][] {{"That Darn Gray Cat", "5"},
            {"Fort Utopia", "10"},
            {"Count Sheep", "9"},
            {"Ms Traitour", "4"},
            {"Real Princess", "3"},
            {"Pa Kettle", "5"},
            {"Gin Stinger", "6"}};
    }

    public void showPaddock() {
        System.out.println("Horses: ");
        for (int i = 0; i < raceHorses.length; i++) {
            String status = i + 1 == Integer.parseInt(currentWinningHorseNumber) ? "won" : "lost";
            System.out.println(String.format("%d,%s,%s,%s", i + 1, raceHorses[i][0], raceHorses[i][1], status));
        }
    }

    public Paddock() {
        this.raceHorses = initRaceHorses();
        this.raceHorsesCount = this.raceHorses.length;
        this.currentWinningHorseNumber = "1";
        this.currentWinningHorseName =
            this.raceHorses[Integer.parseInt(this.currentWinningHorseNumber) - 1][0];
    }

    public String[][] getRaceHorses() {
        return raceHorses;
    }

    public void setRaceHorses(String[][] raceHorses) {
        this.raceHorses = raceHorses;
    }

    public int getRaceHorsesCount() {
        return raceHorsesCount;
    }
    public void setRaceHorsesCount(int racehorsesCount) {
        this.raceHorsesCount = racehorsesCount;
    }

    public String getCurrentWinningHorseNumber() {
        return currentWinningHorseNumber;
    }

    public void setCurrentWinningHorseNumber(String currentWinningHorseNumber) {
        this.currentWinningHorseNumber = currentWinningHorseNumber;
    }

    public String getCurrentWinningHorseName() {
        return currentWinningHorseName;
    }
}
