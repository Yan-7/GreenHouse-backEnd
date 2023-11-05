package com.agrobackEnd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Optional;

@Service
public class GreenHouseService {

    @Autowired
    private GreenHouseRepository greenHouseRepository1;


    public GreenHouse getGreenHouse(int id) {
        Optional<GreenHouse> optGreenHouse = greenHouseRepository1.findById(id);
        GreenHouse greenHouse = optGreenHouse.get();
        return greenHouse;
    }

    public GreenHouse updateWaterLevel(int id, int newLevel) {
        Optional<GreenHouse> optionalGreenHouse = greenHouseRepository1.findById(id);
        GreenHouse greenHouse = optionalGreenHouse.get();
        greenHouse.setWaterLevel(newLevel);
        return greenHouseRepository1.save(greenHouse);
    }

    public GreenHouse updateFertilizeLevel(int id, int newLevel) {
        Optional<GreenHouse> optionalGreenHouse = greenHouseRepository1.findById(id);
        GreenHouse greenHouse = optionalGreenHouse.get();
        greenHouse.setFertilizeLevel(newLevel);
        return greenHouseRepository1.save(greenHouse);
    }

    public GreenHouse updateLightLevel(int id, int newLevel) {
        Optional<GreenHouse> optionalGreenHouse = greenHouseRepository1.findById(id);
        GreenHouse greenHouse = optionalGreenHouse.get();
        greenHouse.setLightLevel(newLevel);
        return greenHouseRepository1.save(greenHouse);
    }


}
