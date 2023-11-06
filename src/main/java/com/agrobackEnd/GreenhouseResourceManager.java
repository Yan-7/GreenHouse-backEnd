package com.agrobackEnd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Optional;

@Service
@EnableScheduling
public class GreenhouseResourceManager {

    @Autowired
    private GreenHouseRepository greenHouseRepository1;

    private  volatile  int customWaterLevel = 200;

    // Method to update the custom water level
    public void setCustomWaterLevel(int level) {
        this.customWaterLevel = level;
    }

    // Scheduled task to check and update water level based on the custom level
    @Scheduled(fixedRate = 60_000) //every one minute
    public void checkWaterPlants() {
        Optional<GreenHouse> optGreenHouse = greenHouseRepository1.findById(1);
        if (!optGreenHouse.isPresent()) {
            System.out.println("Greenhouse not found");
            return;
        }
        GreenHouse greenHouse = optGreenHouse.get();
        if (greenHouse.getWaterLevel() < customWaterLevel) {
            greenHouse.setWaterLevel(1000); // This should be set based on some condition or calculation
            greenHouseRepository1.save(greenHouse);
            System.out.println("Greenhouse watered, new level is: " + greenHouse.getWaterLevel());
        }
    }




//    @Scheduled(fixedRate = 1_000) //
//    public void initiateWatering() {
//        Optional<GreenHouse> optGreenHouse = greenHouseRepository1.findById(1);
//        if (optGreenHouse.isPresent()) {
//            GreenHouse greenHouse = optGreenHouse.get();
//            if (greenHouse.getWaterLevel() < 200) {
//                greenHouse.setWaterLevel(1000);
//                greenHouseRepository1.save(greenHouse);
//                System.out.println("greenhouse watered, new water level is: " + greenHouse.getWaterLevel());
//
//            }
//        } else System.out.println("problem fetching greenhouse data");
//    }

    @Scheduled(fixedRate = 1_000)
    public void initiateFertilizing() {
        Optional<GreenHouse> optGreenHouse = greenHouseRepository1.findById(1);
        if (optGreenHouse.isPresent()) {
            GreenHouse greenHouse1 = optGreenHouse.get();
            if (greenHouse1.getFertilizeLevel() <200) {
                greenHouse1.setFertilizeLevel(greenHouse1.getFertilizeLevel() + 1000);
                greenHouseRepository1.save(greenHouse1);
                System.out.println("initiatedFertilizing, new level is: " + greenHouse1.getFertilizeLevel());
            }
        }

    }

    @Scheduled(fixedRate = 60_000) //check every minute
    public void controlLight() {
        LocalTime timeNow = LocalTime.now();
        LocalTime night = LocalTime.of(18,0);
        LocalTime day = LocalTime.of(7,0);

        Optional<GreenHouse> optGreenHouse = greenHouseRepository1.findById(1);
        if (!optGreenHouse.isPresent()) {
            System.out.println("cannot find green house repository");
            return;
        }
        GreenHouse greenHouse1 = optGreenHouse.get();

        if (timeNow.isAfter(night)) {
            greenHouse1.setLightLevel(800);
            System.out.println(timeNow + "------> night time - lights on");
        }
        if (timeNow.isAfter(day)) {
            greenHouse1.setLightLevel(0);
            System.out.println(timeNow + " -----> day time - lights off");
        }
        greenHouseRepository1.save(greenHouse1);
    }

}
