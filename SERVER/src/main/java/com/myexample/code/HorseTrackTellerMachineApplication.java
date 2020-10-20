package com.myexample.code;

import com.myexample.code.service.HorseTrackTellerMachineService;

public class HorseTrackTellerMachineApplication {

	public static void main(String[] args) {
		HorseTrackTellerMachineService horseTrackTellerMachineService = new HorseTrackTellerMachineService();

		horseTrackTellerMachineService.run();
	}
}
