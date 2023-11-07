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

    private int greenhouseId = 1;

    private int minWaterLevel = 10  ;
    private int maxWaterLevel = 20;

    private int minFertilizeLevel = 30  ;
    private int maxFertilizeLevel = 40;
//----------------------------------------------------------

    // Method to set the parameters for the controlWater task
    public void setWaterParams(int id, int minLevel, int maxLevel) {
        this.greenhouseId = id;
        this.minWaterLevel = minLevel;
        this.maxWaterLevel = maxLevel;
    }

    public void setFertilizeParams(int id, int minLevel, int maxLevel) {
        this.greenhouseId = id;
        this.minFertilizeLevel = minLevel;
        this.maxFertilizeLevel = maxLevel;
    }
    // Scheduled task to check and update water level based on the custom level
    @Scheduled(fixedRate = 5_000)
    public void WaterManager() {
        Optional<GreenHouse> optGreenHouse = greenHouseRepository1.findById(1);
        if (!optGreenHouse.isPresent()) {
            System.out.println("Greenhouse not found - GreenhouseResourceManager - controlWater function");
            return;
        }
        GreenHouse greenHouse = optGreenHouse.get();
        int currentWaterLevel = greenHouse.getWaterLevel();
        if (currentWaterLevel <= minWaterLevel) {
            greenHouse.setWaterLevel(maxWaterLevel);
            greenHouseRepository1.save(greenHouse);
            System.out.println("control water function updated water level: " + greenHouse.getWaterLevel());
        }
    }

    @Scheduled(fixedRate = 6_000)
    public void FertilizeManager() {
        Optional<GreenHouse> optGreenHouse = greenHouseRepository1.findById(1);
        if (!optGreenHouse.isPresent()) {
            System.out.println("Greenhouse not found - GreenhouseResourceManager - controlFertilize function");
            return;
        }
        GreenHouse greenHouse = optGreenHouse.get();
        int currentFertilizeLevel = greenHouse.getFertilizeLevel();
        if (currentFertilizeLevel <= minFertilizeLevel) {
            greenHouse.setFertilizeLevel(maxFertilizeLevel);
            greenHouseRepository1.save(greenHouse);
            System.out.println("control fertilize function initiated, new level is: " + greenHouse.getFertilizeLevel());
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
