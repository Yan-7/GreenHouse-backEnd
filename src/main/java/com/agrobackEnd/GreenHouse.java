package com.agrobackEnd;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class GreenHouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int waterLevel;

    private int minWater;
    private int maxWater;

    private int fertilizeLevel;
    private int minFertilize;
    private int maxFertilize;

    private  int lightLevel;
    private LocalTime lightonTime;
    private LocalTime lightOffTime;
}
