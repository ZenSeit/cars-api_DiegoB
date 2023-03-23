package com.diegob.carsapi.usecases;

import com.diegob.carsapi.domain.dto.CarDTO;
import com.diegob.carsapi.publisher.CarPublisher;
import com.diegob.carsapi.repository.ICarRepository;
import com.diegob.carsapi.usecases.interfaces.RentCar;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class RentCarUseCase implements RentCar {

    private final ICarRepository carRepository;

    private final ModelMapper mapper;
    private final CarPublisher carPublisher;

    @Override
    public Mono<CarDTO> rent(String carId, String driverId) {
        return carRepository
                .findById(carId)
                .switchIfEmpty(Mono.error(new Throwable("Car not exist!")))
                .flatMap(car -> {
                    if(!car.isRented()){
                        car.setRented(true);
                        return carRepository.save(car);
                    }
                    return Mono.error(new Throwable("Car is already rented"));
                })
                .map(car -> mapper.map(car, CarDTO.class))
                .doOnSuccess(carDTO -> {

                    try {
                        carPublisher.publish(carDTO,driverId);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
