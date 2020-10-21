package com.myexample.code.utils;

import com.myexample.code.domain.Atm;
import com.myexample.code.domain.Paddock;

public class HorseTrackTellerMachineUtils {

    public static boolean isValidNewBetCommand(String command) {
        return isValidInt(command);
    }

    public static boolean isValidHorseNumber(int numberOfRaceHorses, String horseNumber) {
        return  (isValidInt(horseNumber) && (0 < Integer.parseInt(horseNumber)
            && Integer.parseInt(horseNumber) <= numberOfRaceHorses));
    }

    public static void determinePayout(Atm atm, Paddock paddock, String betCommand, String wager) {
        if (!isValidHorseNumber(paddock.getRaceHorsesCount(), betCommand)) {
            System.out.println("Invalid Horse Number: " + betCommand);
            return;
        }
        if (!isValidBet(wager)) {
            System.out.println("Invalid Bet: " + wager);
            return;
        }
        if (!isWinningBet(paddock.getCurrentWinningHorseNumber(), betCommand)
            && isValidBet(wager)) {
            noPayout(paddock, betCommand);
            return;
        }
        if (isWinningBet(paddock.getCurrentWinningHorseNumber(), betCommand)
            && isValidBet(wager)) {
            payout(atm, paddock, wager);
        }
    }

    public static void noPayout(Paddock paddock, String horseNumber) {
        System.out.println("No Payout: " + paddock.getRaceHorses()[Integer.parseInt(horseNumber) -1][0]);
    }

    public static int payout(Atm atm, Paddock paddock, String wager) {
        int winningHorseNumber = Integer.parseInt(paddock.getCurrentWinningHorseNumber());
        int payout = calculatePayout(paddock, winningHorseNumber, wager);
        int[][] billsToDispense;
        if (atm.isInsufficientFunds(payout) || (billsToDispense = atm.getBillsToDispense(payout)) == null) {
            System.out.println("Insufficient Funds: " + payout);
            return 0;
        }
        System.out.println(String.format("Payout: %s,%d", paddock.getRaceHorses()[winningHorseNumber -1][0], payout));
        atm.debitAtmAndDispenseBills(billsToDispense);
        return payout;
    }

    public static int calculatePayout(Paddock paddock, int winningHorseNumber, String wager) {
        int raceHorseOdds = Integer.parseInt(paddock.getRaceHorses()[winningHorseNumber -1][1]);
        return Integer.parseInt(wager) * raceHorseOdds;
    }

    public static boolean isValidBet(String wager) {
        return isValidInt(wager);
    }

    public static boolean isWinningBet(String winningHorseNumber, String horseNumber) {
        return winningHorseNumber.equals(horseNumber);
    }

    /**
     * attempt to set the New Winning Horse Number. If the new winning horse number is valid
     * (a horse number of one of the horses in the Paddock), then the current winning horse number
     * is changed new winning horse number. If it is not valid, current winning horse number is
     * unchanged.
     * @param numberOfRaceHorses - number of race horses currently in the Paddock
     * @param currentWinningHorseNumber - current winning number
     * @param newWinningHorseNumber - proposed new winning horse number
     * @return - horse number to set the current winning horse number to
     */
    public static String setNewWinningHorseNumber(int numberOfRaceHorses, String currentWinningHorseNumber,
                                                  String newWinningHorseNumber) {
        if (!isValidHorseNumber(numberOfRaceHorses, newWinningHorseNumber)) {
            System.out.println("Invalid Horse Number: " + newWinningHorseNumber);
            return currentWinningHorseNumber;
        }
        return newWinningHorseNumber;
    }

    public static boolean isValidInt(String value) {
        boolean truthValue = true;
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException numberFormatException) {
            truthValue = false;
        }
        return truthValue;
    }

    public static void deleteRaceHorse(Paddock paddock, String horseNumber) {
        if (isValidHorseNumber(paddock.getRaceHorsesCount(), horseNumber)) {
            paddock.deleteRaceHorse(Integer.parseInt(horseNumber));
        }
    }
}
