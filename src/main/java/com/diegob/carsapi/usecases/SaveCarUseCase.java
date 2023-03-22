package com.diegob.carsapi.usecases;

import com.diegob.carsapi.domain.collection.Car;
import com.diegob.carsapi.domain.dto.CarDTO;
import com.diegob.carsapi.repository.ICarRepository;
import com.diegob.carsapi.usecases.interfaces.SaveCar;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class SaveCarUseCase implements SaveCar {

    private final ICarRepository carRepository;

    private final ModelMapper mapper;

    @Override
    public Mono<CarDTO> save(CarDTO carDTO) {
        return carRepository.save(mapper.map(carDTO, Car.class))
                .switchIfEmpty(Mono.empty())
                .map(car -> mapper.map(car, CarDTO.class))
                .onErrorResume(Mono::error);
    }
}
