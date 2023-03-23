package com.diegob.carsapi.publisher;

import com.diegob.carsapi.domain.dto.CarDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarEvent {

    private String idDriver;
    private CarDTO carRented;
    private String eventType;

}
