package com.diegob.carsapi.usecases;

import com.diegob.carsapi.domain.collection.Car;
import com.diegob.carsapi.repository.ICarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetAllCarsUseCaseTest {

    @Mock
    ICarRepository repository;

    ModelMapper mapper;

    GetAllCarsUseCase getAllCarsUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        getAllCarsUseCase = new GetAllCarsUseCase(repository,mapper);
    }

    @Test
    void getAllCars(){

        var car1 = new Car(
                "Mazda",
                "White",
                2019,
                "CBD186"
        );

        var car2 = new Car(
                "Chevrolet",
                "Mouve",
                2011,
                "CZD172"
        );

        var fluxCars = Flux.just(car1, car2);

        Mockito.when(repository.findAll()).thenReturn(fluxCars);

        var response = getAllCarsUseCase.get();

        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();

        Mockito.verify(repository).findAll();


    }

}