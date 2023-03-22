package com.diegob.carsapi.usecases;

import com.diegob.carsapi.domain.dto.CarDTO;
import com.diegob.carsapi.repository.ICarRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class GetAllCarsUseCase implements Supplier<Flux<CarDTO>> {

    private final ICarRepository carRepository;

    private final ModelMapper mapper;


    @Override
    public Flux<CarDTO> get() {
        return carRepository.findAll()
                .switchIfEmpty(Flux.empty())
                .map(car -> mapper.map(car, CarDTO.class));
    }
}
