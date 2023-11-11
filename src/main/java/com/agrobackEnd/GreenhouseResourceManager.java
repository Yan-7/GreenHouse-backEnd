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

    private int lightLevel = 0;
    private  LocalTime lightOn = null;
    private  LocalTime lightOff = null;

//----------------------------------------------------------

    // used to control the databases for the lightManager function
    public void setLightParams(int id, int lightLevel, LocalTime lightOn, LocalTime lightOff) {
        this.greenhouseId = id;
        this.lightLevel = lightLevel;
        this.lightOn = lightOn;
        this.lightOff = lightOff;
        Optional<GreenHouse> optGreenHouse = greenHouseRepository1.findById(id);
        if (!optGreenHouse.isPresent()) {
            System.out.println("greenhouseManager - setLightParams - cannot find greenhouse");
        }
        GreenHouse greenHouse = optGreenHouse.get();
        greenHouse.setLightLevel(lightLevel);
        greenHouse.setLightonTime(lightOn);
        greenHouse.setLightOffTime(lightOff);
        greenHouseRepository1.save(greenHouse);
        System.out.println("setLightParams - updated new info");
    }
    // Method to set the parameters for the controlWater task
    public void setWaterParams(int id, int minLevel, int maxLevel) {
        this.greenhouseId = id;
        this.minWaterLevel = minLevel;
        this.maxWaterLevel = maxLevel;
        Optional<GreenHouse> optGreenHouse = greenHouseRepository1.findById(id);
        if (!optGreenHouse.isPresent()) {
            System.out.println("greenhouse not found");
            return;
        }
        GreenHouse greenHouse = optGreenHouse.get();
        greenHouse.setMinWater(minLevel);
        greenHouse.setMaxWater(maxLevel);
        greenHouseRepository1.save(greenHouse);
        System.out.println("setWaterParams updated to the following params: " + minLevel + " " + maxLevel);
    }

    public void setFertilizeParams(int id, int minLevel, int maxLevel) {
        this.greenhouseId = id;
        this.minFertilizeLevel = minLevel;
        this.maxFertilizeLevel = maxLevel;
        Optional<GreenHouse> optGreenHouse = greenHouseRepository1.findById(id);
        if (!optGreenHouse.isPresent()) {
            System.out.println("setFertilizeParams - cannot find green house");
            return;
        }
        GreenHouse greenHouse = optGreenHouse.get();
        greenHouse.setMinFertilize(minLevel);
        greenHouse.setMaxFertilize(maxLevel);
        greenHouseRepository1.save(greenHouse);
        System.out.println("setFertilizeParams updated to the following params: " + minLevel + " " + maxLevel);
    }

    public GreenHouse getGreenHouse(int id) {
        Optional<GreenHouse> optGreenHouse = greenHouseRepository1.findById(id);
        GreenHouse greenHouse = optGreenHouse.get();
        return greenHouse;
    }

    //light
    @Scheduled(fixedRate = 20_000) //check every minute
    public void lightManager() {
        LocalTime timeNow = LocalTime.now();

        if (lightOn == null || lightOff == null) {
            System.out.println("Light parameters are not initialized yet.");
            return;
        }
        Optional<GreenHouse> optGreenHouse = greenHouseRepository1.findById(1);
        if (!optGreenHouse.isPresent()) {
            System.out.println("cannot find green house repository");
            return;
        }
        GreenHouse greenHouse1 = optGreenHouse.get();

        // Assume 'night' and 'day' are meant to be 'lightOff' and 'lightOn'
        if (timeNow.isAfter(lightOn) && timeNow.isBefore(lightOff)) {
            greenHouse1.setLightLevel(this.lightLevel);
            System.out.println(timeNow + "------>  lights on");
        } else {
            greenHouse1.setLightLevel(0);
            System.out.println(timeNow + " ----->  lights off");
        }
        greenHouseRepository1.save(greenHouse1);
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
            System.out.println("GreenhouseResourceManager  WaterManager: " + greenHouse.getWaterLevel());
        }
    }

    @Scheduled(fixedRate = 10_000)
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
            System.out.println("GreenhouseResourceManager FertilizeManager" +
                    ": " + greenHouse.getFertilizeLevel());
        }
    }


}
