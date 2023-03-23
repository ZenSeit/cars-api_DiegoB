package com.diegob.carsapi.usecases.interfaces;

import com.diegob.carsapi.domain.dto.CarDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface RentCar {
    Mono<CarDTO> rent(String idCar, String idDriver);
}
