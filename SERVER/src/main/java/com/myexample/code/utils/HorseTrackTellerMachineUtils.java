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
        System.out.println("No Payout: " + paddock.getHorseName(horseNumber));
    }

    public static int payout(Atm atm, Paddock paddock, String wager) {
        int payout = calculatePayout(paddock, wager);
        if (atm.isInsufficientFunds(payout) || !atm.hasCurrencyToDispense(payout)) {
            System.out.println("Insufficient Funds: " + payout);
            return 0;
        }
        System.out.println(String.format("Payout: %s,%d", paddock.getHorseName(paddock.getCurrentWinningHorseNumber()), payout));
        atm.debitAtmAndDispenseCurrency(payout);
        return payout;
    }

    public static int calculatePayout(Paddock paddock, String wager) {
        int raceHorseOdds = Integer.parseInt(paddock.getHorseOdds(paddock.getCurrentWinningHorseNumber()));
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

    public static void addRaceHorse(Paddock paddock, String name) {
        if (name == null || name.isEmpty()) {
            System.out.println("Empty Horse Name ");
            return;
        }
        paddock.addRaceHorse(name);
    }

    public static void deleteRaceHorse(Paddock paddock, String horseNumber) {
        if (isValidHorseNumber(paddock.getRaceHorsesCount(), horseNumber)) {
            paddock.deleteRaceHorse(Integer.parseInt(horseNumber));
        }
    }

    public static void addCurrency(Atm atm, String amounts) {
        String[] cash = amounts.split(" ");
        if (cash.length < 2 || !isValidInt(cash[0]) || !isValidInt(cash[1])) {
            System.out.println("Invalid Denomination and Count: " + amounts);
            return;
        }
        int denomination = Integer.parseInt(cash[0]);
        int denominationCount = Integer.parseInt(cash[1]);
        atm.addCash(denomination, denominationCount);
    }

    public static void removeCurrency(Atm atm, String amounts) {
        String[] cash = amounts.split(" ");
        if (cash.length < 2 || !isValidInt(cash[0]) || !isValidInt(cash[1])) {
            System.out.println("Invalid Denomination and Count: " + amounts);
            return;
        }
        int denomination = Integer.parseInt(cash[0]);
        int denominationCount = Integer.parseInt(cash[1]);
        atm.removeCash(denomination, denominationCount);
    }
}
