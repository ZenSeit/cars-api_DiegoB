package com.diegob.carsapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {

    private String id;
    private String brand;
    private String color;
    private int model;
    private String licensePlate;
    private boolean isRented;

    public CarDTO(String brand, String color, int model, String licensePlate) {
        this.brand = brand;
        this.color = color;
        this.model = model;
        this.licensePlate = licensePlate;
    }
}
