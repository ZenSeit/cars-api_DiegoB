package com.diegob.carsapi.usecases;

import com.diegob.carsapi.domain.dto.CarDTO;
import com.diegob.carsapi.repository.ICarRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class GetCarByIdUseCase implements Function<String, Mono<CarDTO>> {

    private final ICarRepository carRepository;

    private final ModelMapper mapper;

    @Override
    public Mono<CarDTO> apply(String id) {
        return carRepository.findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Car not found")))
                .map(car -> mapper.map(car, CarDTO.class))
                .onErrorResume(Mono::error);
    }
}
