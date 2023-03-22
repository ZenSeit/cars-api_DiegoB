package com.diegob.carsapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {

    private String id = UUID.randomUUID().toString().substring(0, 10);
    private String brand;
    private String color;
    private int model;
    private String licensePlate;
    private boolean isRented;
}
