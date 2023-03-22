package com.diegob.carsapi.usecases.interfaces;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface DeleteCar {
    Mono<Void> delete(String id);
}
