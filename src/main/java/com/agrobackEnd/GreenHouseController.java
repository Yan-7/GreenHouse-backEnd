package com.agrobackEnd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Map;


@RestController
@RequestMapping("/api/green-house")
public class GreenHouseController {

    @Autowired
    private GreenhouseResourceManager greenhouseResourceManager1;

    @GetMapping("/{id}")
    public GreenHouse getGreenHouse(@PathVariable int id) {
        GreenHouse greenHouse = greenhouseResourceManager1.getGreenHouse(id);
        System.out.println("controller getGreenHouse function: " + greenHouse);
        return greenHouse;
    }

    // Renamed to controlWaterLevel to avoid conflict with controlLight
    @PutMapping("/{id}/water-level")
    public String controlWaterLevel(@PathVariable Integer id, @RequestBody Map<String, String> levels) {
        Integer min = Integer.parseInt(levels.get("min"));
        Integer max = Integer.parseInt(levels.get("max"));
        greenhouseResourceManager1.setWaterParams(id, min, max);
        System.out.println("waterController: " + id + " " + min + " " + max);
        return "Water controller started";
    }
    @PutMapping("/{id}/light-level")
    public String controlLight(@PathVariable Integer id, @RequestBody Map<String, Integer> levels) {
        LocalTime lightsOn = LocalTime.ofSecondOfDay(levels.get("on"));
        LocalTime lightsOff = LocalTime.ofSecondOfDay(levels.get("off"));
//        Integer lightLevel = levels.get("level");
        Integer lightLevel = levels.containsKey("level") ? levels.get("level") : 0; // default value if null

        greenhouseResourceManager1.setLightParams(id,lightLevel, lightsOn, lightsOff);
        System.out.println("waterController: " + id +" " + lightsOn+ " " +lightsOff);
        return "water controller started";
    }

    @PutMapping("/{id}/fertilize-level")
    public String fertilizerController(@PathVariable Integer id, @RequestBody Map<String,Integer> levels) {
        Integer min = levels.get("min");
        Integer max = levels.get("max");
        greenhouseResourceManager1.setFertilizeParams(id,min, max);
        System.out.println("fertilizerController : " + id + " " + min + " " + max);
        return "returning fertilizerController from backend";
    }

//----------------------------------------
}
