package com.agrobackEnd;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableScheduling
public class AgroBackEndApplication implements CommandLineRunner {
	@Autowired
	private GreenHouseService greenHouseService;

	@Autowired
	private GreenHouseController greenHouseController1;

	public static void main(String[] args) {
		SpringApplication.run(AgroBackEndApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	greenHouseController1.waterController(1,1000,1100);
	greenHouseController1.fertilizerController(1,1300,1400);
	}
}
		//	<-------------------- tests -------------------->

//		System.out.println("water: " +waterLevelController1.getWaterLevel(1));
//		System.out.println(waterLevelService1.getWaterLevel(1));
//
//		System.out.println(waterLevelService1.updateWaterLevel(1,23));
//		System.out.println(waterLevelController1.updateWaterLevel(1,132));

//		----------------------------------------------------------
//		System.out.println(fertilizeRepository.findById(1));
//		System.out.println("fert: " + fertilizeController.getFertilizeLevel(1));

//		System.out.println(fertilizeService.updateFertilizeLevel(1,2030));

//		System.out.println("flevel: " + fertilizeService.getFertilizeLevel(1));
		//---------------------------------------------------------------------
//		System.out.println(greenHouseService.getGreenHouse(1));
//		System.out.println(greenHouseController.getGreenHouse(1));
		//-------------------------------------------------------
//		System.out.println(greenHouseService.updateLightLevel(1,1000));



