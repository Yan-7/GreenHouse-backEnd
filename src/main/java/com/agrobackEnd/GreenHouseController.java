package com.agrobackEnd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


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
    public String waterController(int id,int min, int max) {
        greenhouseResourceManager1.setWaterParams(id, min, max);
        System.out.println("waterController: " + id +" " + min+ " " +max);
        return "water controller started";
    }



    @PutMapping("/{id}/fertilize-level")
//    @PathVariable int id, @RequestBody Map<String, Integer> levels
    public String fertilizerController(int id, int min, int max) {
        greenhouseResourceManager1.setFertilizeParams(id,min,max);
        System.out.println("fertilizerController: " + id + " " + min + " " + max);
        return "fertilize controller started";
    }



//----------------------------------------
}
