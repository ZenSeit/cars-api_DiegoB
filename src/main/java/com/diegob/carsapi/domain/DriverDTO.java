package com.diegob.carsapi.domain;

import com.diegob.carsapi.domain.collection.Car;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class DriverDTO {
    private String id;
    private String name;
    private String lastName;
    private int age;
    private String email;
    private List<Car> rentedCars =  new ArrayList<>();
}
