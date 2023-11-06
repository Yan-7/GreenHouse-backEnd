package com.agrobackEnd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/green-house")
public class GreenHouseController {


    @Autowired
    private GreenHouseService greenHouseService;

    @Autowired
    private GreenhouseResourceManager greenhouseResourceManager1;

    @GetMapping("/{id}")
    public GreenHouse getGreenHouse(@PathVariable int id) {
        GreenHouse greenHouse = greenHouseService.getGreenHouse(1);
        System.out.println("controller getGreenHouse function: " + greenHouse);
        return greenHouse;
    }



    @PutMapping("/{id}/water-level")
    public GreenHouse updateWaterLevel(@PathVariable int id, @RequestBody Map<String, Integer> levels) {
        int newLevel = levels.get("newLevel");

        }
        return greenHouseService.updateWaterLevel(id, newLevel);
    }

    @PutMapping("/{id}/fertilize-level")
    public GreenHouse updateFertilizerLevel(@PathVariable int id, @RequestBody Map<String, Integer> levels) {
        int newLevel = levels.get("newLevel");
        return  greenHouseService.updateFertilizeLevel(id,newLevel);
    }

    @PutMapping("/{id}/light-level")
    public GreenHouse updateLightLevel(@PathVariable int id, @RequestBody Map<String, Integer> levels) {
        int newLevel = levels.get("newLevel");
        return  greenHouseService.updateLightLevel(id,newLevel);
    }


//----------------------------------------
}
