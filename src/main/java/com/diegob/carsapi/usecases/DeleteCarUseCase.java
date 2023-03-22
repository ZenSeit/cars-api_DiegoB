package com.diegob.carsapi.usecases;

import com.diegob.carsapi.repository.ICarRepository;
import com.diegob.carsapi.usecases.interfaces.DeleteCar;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DeleteCarUseCase implements DeleteCar {

    private final ICarRepository carRepository;
    @Override
    public Mono<Void> delete(String id) {
        return carRepository.findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Car not found")))
                .flatMap(carRepository::delete);
    }
}
