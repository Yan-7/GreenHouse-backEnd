package com.agrobackEnd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class LevelsReducer {

    @Autowired
    GreenHouseRepository greenHouseRepository1;

    @Scheduled(fixedRate = 1000)
    public void reduceWater()  {
        Optional<GreenHouse> optGreenHouse1 = greenHouseRepository1.findById(1);
        if (!optGreenHouse1.isPresent()) {
            System.err.println("GreenHouse not found");
            return;  // Exit the method
        }
        GreenHouse greenHouse1 = optGreenHouse1.get();
        if (greenHouse1.getWaterLevel()>0) {
            greenHouse1.setWaterLevel(greenHouse1.getWaterLevel() - 10);
            greenHouseRepository1.save(greenHouse1);

        }
    }

    @Scheduled(fixedRate = 10000) {
        
    }
}
