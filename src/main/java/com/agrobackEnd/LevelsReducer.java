package com.agrobackEnd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.util.Optional;


@Service
public class LevelsReducer {

    @Autowired
    GreenHouseRepository greenHouseRepository1;

    @Scheduled(fixedRate = 10_000)
    public void reduceWater() {
        for (int i = 1; i <=6 ; i++) {
            Optional<GreenHouse> optGreenHouse1 = greenHouseRepository1.findById(i);
            if (!optGreenHouse1.isPresent()) {
                System.err.println("GreenHouse not found");
                return;  // Exit the method
            }
            GreenHouse greenHouse1 = optGreenHouse1.get();
            if (greenHouse1.getWaterLevel() > 0) {
                greenHouse1.setWaterLevel(greenHouse1.getWaterLevel() - 10);
                greenHouseRepository1.save(greenHouse1);
                System.out.println("water reduced for greenhouse " + greenHouse1.getId() + " , level now: " + greenHouse1.getWaterLevel());
            }
        }

    }

    @Scheduled(fixedRate = 20_000)
    public void reduceFertilize() {
        for (int i = 1; i <=6 ; i++) {
            Optional<GreenHouse> optGreenHouse1 = greenHouseRepository1.findById(i);
            if (optGreenHouse1.isPresent()) {
                GreenHouse greenHouse1 = optGreenHouse1.get();
                if (greenHouse1.getFertilizeLevel() >0 ) {
                    greenHouse1.setFertilizeLevel(greenHouse1.getFertilizeLevel() - 10);
                    greenHouseRepository1.save(greenHouse1);
                    System.out.println("fertilize reduced for greenhouse " + greenHouse1.getId() + " , level now: " + greenHouse1.getFertilizeLevel());
                }
            } else {
                System.out.println("cannot find greenhouse object");
            }
        }

    }


}
