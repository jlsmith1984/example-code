package com.myexample.code.service;

import java.util.Scanner;

import static com.myexample.code.utils.HorseTrackTellerMachineUtils.*;
import com.myexample.code.domain.Atm;
import com.myexample.code.domain.Paddock;

public class HorseTrackTellerMachineService {
    private static final Scanner scanner = new Scanner(System.in);

    public void run() {
        Atm atm = new Atm();
        Paddock paddock = new Paddock();

        String command;  // scanning for string allows easy recognition of large integer inputs
        String input;
        do {
            atm.showInventory();
            paddock.showPaddock();
            command = scanner.next().trim().toLowerCase();
            input = scanner.nextLine().trim();
            switch (command) {
                case "r": {
                    atm.restockInventory();
                    break;
                }
                case "w": {
                    paddock.setCurrentWinningHorseNameAndNumber(
                        setNewWinningHorseNumber(paddock.getRaceHorsesCount(), paddock.getCurrentWinningHorseNumber(), input));
                    break;
                }
                case "ca": {
                    addCurrency(atm, input);
                    break;
                }
                case "cr": {
                    removeCurrency(atm, input);
                    break;
                }
                case "ha": {
                    addRaceHorse(paddock, input);
                    break;
                }
                case "hd": {
                    deleteRaceHorse(paddock, input);
                    break;
                }
                case "q": {
                    break;
                }
                default: {
                    if (!isValidNewBetCommand(command)) {
                        System.out.println("Invalid Command: " + command + " " + input);
                    }
                    else {
                        determinePayout(atm, paddock, command, input);
                    }
                }
            }
        } while (!"Q".equalsIgnoreCase(command));


        scanner.close();
    }
}
