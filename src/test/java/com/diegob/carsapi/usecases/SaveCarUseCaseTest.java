package com.diegob.carsapi.usecases;

import com.diegob.carsapi.domain.collection.Car;
import com.diegob.carsapi.domain.dto.CarDTO;
import com.diegob.carsapi.repository.ICarRepository;
import org.junit.jupiter.api.BeforeEach;
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
class SaveCarUseCaseTest {

    @Mock
    ICarRepository repository;

    ModelMapper mapper;

    SaveCarUseCase saveCarUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        saveCarUseCase = new SaveCarUseCase(repository,mapper);
    }

    @Test
    void saveStudent(){

        var car = new Car(
                "Mazda",
                "White",
                2019,
                "CBD186"
        );

        Mockito.when(repository.save(ArgumentMatchers.any(Car.class))).thenReturn(Mono.just(car));

        var response = saveCarUseCase.save(mapper.map(car, CarDTO.class));

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(repository).save(ArgumentMatchers.any(Car.class));

    }

    @Test
    void saveCar_Fail(){

        var car = new Car(
                "Mazda",
                "White",
                2019,
                null
        );

        Mockito.when(repository.save(ArgumentMatchers.any(Car.class))).thenReturn(Mono.empty());

        var response = saveCarUseCase.save(mapper.map(car, CarDTO.class));

        StepVerifier.create(response)
                .expectError(Throwable.class);

        Mockito.verify(repository).save(ArgumentMatchers.any(Car.class));

    }



}