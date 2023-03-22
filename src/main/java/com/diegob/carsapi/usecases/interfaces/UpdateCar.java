package com.diegob.carsapi.usecases.interfaces;

import com.diegob.carsapi.domain.dto.CarDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface UpdateCar {

    Mono<CarDTO> update(String id, CarDTO carDTO);
}
