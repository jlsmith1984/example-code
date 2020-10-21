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

    /**
     * Add a racehorse to the paddock's racehorses. Track calculates its odds of winning
     * @param name - name of added race horse
     */
    public void addRaceHorse(String name) {
        int min = 2;
        int max = 19;
        String odds = Integer.toString(new Random().nextInt(((max - min) + 1) + min));
        String[][] addedRaceHorses = new String[raceHorses.length + 1][2];
        System.arraycopy(raceHorses,0, addedRaceHorses, 0, raceHorses.length);
        addedRaceHorses[raceHorses.length][0] = name;
        addedRaceHorses[raceHorses.length][1] = odds;
        this.raceHorses = addedRaceHorses;
        setRaceHorsesCount(raceHorses.length);
    }

    public void deleteRaceHorse(int raceHorseNumber) {
        if (0 > raceHorseNumber || raceHorseNumber > raceHorses.length) {
            System.out.println("Invalid Horse Number: " + raceHorseNumber);
            return;
        }
        String[][] deletedRaceHorses = new String[raceHorses.length - 1][2];
        System.arraycopy(raceHorses,0, deletedRaceHorses, 0, raceHorseNumber -1);
        System.arraycopy(raceHorses,raceHorseNumber, deletedRaceHorses, raceHorseNumber - 1, deletedRaceHorses.length - (raceHorseNumber -1));
        this.raceHorses = deletedRaceHorses;
    }

    public Paddock() {
        this.raceHorses = initRaceHorses();
        this.raceHorsesCount = this.raceHorses.length;
        this.currentWinningHorseNumber = "1";
        this.currentWinningHorseName =
            this.getHorseName(this.currentWinningHorseNumber);
    }

    public String[][] getRaceHorses() {
        return raceHorses;
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

    public void setCurrentWinningHorseNameAndNumber(String currentWinningHorseNumber) {
        this.currentWinningHorseNumber = currentWinningHorseNumber;
        this.currentWinningHorseName = getHorseName(currentWinningHorseNumber);
    }

    public String getCurrentWinningHorseName() {
        return currentWinningHorseName;
    }

    public String getHorseName(String horseNumber) {
        return this.raceHorses[Integer.parseInt(horseNumber) -1][0];
    }

    public String getHorseOdds(String horseNumber) {
        return this.raceHorses[Integer.parseInt(horseNumber) -1][1];
    }
}
