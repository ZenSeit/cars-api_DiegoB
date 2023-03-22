package com.diegob.carsapi.usecases;

import com.diegob.carsapi.domain.collection.Car;
import com.diegob.carsapi.domain.dto.CarDTO;
import com.diegob.carsapi.repository.ICarRepository;
import com.diegob.carsapi.usecases.interfaces.UpdateCar;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UpdateCarUseCase implements UpdateCar {

    private final ICarRepository carRepository;

    private final ModelMapper mapper;
    @Override
    public Mono<CarDTO> update(String id, CarDTO carDTO) {
        return carRepository.findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Car not found")))
                .flatMap(car -> {
                    carDTO.setId(car.getId());
                    return carRepository.save(mapper.map(carDTO, Car.class))
                            .map(s ->mapper.map(s, CarDTO.class));
                })
                .onErrorResume(Mono::error);
    }
}
