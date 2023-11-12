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

    public void setWaterParams(int id, int minLevel, int maxLevel) {
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
    // used to control the databases for the lightManager function
    public String setLightParams(int id, int lightLevel, LocalTime lightOn, LocalTime lightOff) {
        if ( lightLevel <0) {
            System.out.println("idiot! there is no minus light level, try again");
            return "idiot! there is no minus light level, try again" ;
        }
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
        return "greenHouse setLightParams " + id + " updated light";
    }
    // Method to set the parameters for the controlWater task

    public void setFertilizeParams(int id, int minLevel, int maxLevel) {
        Optional<GreenHouse> optGreenHouse = greenHouseRepository1.findById(id);
        if (!optGreenHouse.isPresent()) {
            System.out.println("GreenhouseResourceManager-setFertilizeParams - cannot find green house " + id);
            return;
        }
        GreenHouse greenHouse = optGreenHouse.get();
        greenHouse.setMinFertilize(minLevel);
        greenHouse.setMaxFertilize(maxLevel);
        greenHouseRepository1.save(greenHouse);
        System.out.println("setFertilizeParams updated to the following params: " + minLevel + " " + maxLevel);
    }

    public GreenHouse getGreenHouse(int id) {
        return greenHouseRepository1.findById(id).orElse(null);
    }

    @Scheduled(fixedRate = 20_000)
    public void lightManager() {
        LocalTime timeNow = LocalTime.now();
        greenHouseRepository1.findAll().forEach(greenHouse -> {
            LocalTime lightOn = greenHouse.getLightonTime();
            LocalTime lightOff = greenHouse.getLightOffTime();
            int lightLevel = greenHouse.getLightLevel();

            if (lightOn != null && lightOff != null) {
                if (timeNow.isAfter(lightOn) && timeNow.isBefore(lightOff)) {
                    greenHouse.setLightLevel(lightLevel);
                } else {
                    greenHouse.setLightLevel(0);
                }
                greenHouseRepository1.save(greenHouse);
            }
        });
    }

    @Scheduled(fixedRate = 5_000)
    public void waterManager() {
        greenHouseRepository1.findAll().forEach(greenHouse -> {
            int currentWaterLevel = greenHouse.getWaterLevel();
            if (currentWaterLevel <= greenHouse.getMinWater()) {
                greenHouse.setWaterLevel(greenHouse.getMaxWater());
                greenHouseRepository1.save(greenHouse);
                System.out.println("waterManager updated " + greenHouse.getId() +  "water level to: " + greenHouse.getFertilizeLevel());
            }
        });
    }

    @Scheduled(fixedRate = 10_000)
    public void fertilizeManager() {
        greenHouseRepository1.findAll().forEach(greenHouse -> {
            int currentFertilizeLevel = greenHouse.getFertilizeLevel();
            if (currentFertilizeLevel <= greenHouse.getMinFertilize()) {
                greenHouse.setFertilizeLevel(greenHouse.getMaxFertilize());
                greenHouseRepository1.save(greenHouse);
                System.out.println("fertilizeManager updated fertilize level to: " + greenHouse.getFertilizeLevel());
            }
        });
    }




}
