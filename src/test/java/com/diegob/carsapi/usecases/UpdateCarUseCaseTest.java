package com.diegob.carsapi.usecases;

import com.diegob.carsapi.domain.collection.Car;
import com.diegob.carsapi.domain.dto.CarDTO;
import com.diegob.carsapi.repository.ICarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateCarUseCaseTest {

    @Mock
    ICarRepository repository;

    ModelMapper mapper;

    UpdateCarUseCase updateCarUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        updateCarUseCase = new UpdateCarUseCase(repository,mapper);
    }

    @Test
    void updateCar(){

        var car = new Car(
                "Mazda",
                "White",
                2019,
                "CBD186"
        );

        var carUpdated = new CarDTO(
                "Mazda",
                "White",
                2019,
                "CBD186"
        );

        carUpdated.setId(car.getId());

        Mockito.when(repository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.just(car));

        Mockito.when(repository.save(ArgumentMatchers.any(Car.class))).thenReturn(Mono.just(mapper.map(carUpdated, Car.class)));

        var response = updateCarUseCase.update("",mapper.map(car, CarDTO.class));

        StepVerifier.create(response)
                .expectNext(carUpdated)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(repository).findById(ArgumentMatchers.anyString());
        Mockito.verify(repository).save(ArgumentMatchers.any(Car.class));

    }

    @Test
    @DisplayName("updateCarInvalidId_Fail")
    void updateCarInvalidId_Fail() {

        var car = new Car(
                "Mazda",
                "White",
                2019,
                "CBD186"
        );

        Mockito.when(repository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.empty());

        var response = updateCarUseCase.update("",mapper.map(car, CarDTO.class));

        StepVerifier.create(response)
                .expectError(Throwable.class);

        Mockito.verify(repository).findById(ArgumentMatchers.anyString());

    }

}