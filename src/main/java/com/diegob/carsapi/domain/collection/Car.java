package com.diegob.carsapi.domain.collection;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@NoArgsConstructor
@Document(collection = "cars")
public class Car {

    @Id
    private String id = UUID.randomUUID().toString().substring(0, 10);
    private String brand;
    private String color;
    private int model;
    private String licensePlate;
    private boolean isRented=false;
}
