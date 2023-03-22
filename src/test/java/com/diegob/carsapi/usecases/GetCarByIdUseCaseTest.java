package com.diegob.carsapi.usecases;

import com.diegob.carsapi.domain.collection.Car;
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
class GetCarByIdUseCaseTest {

    @Mock
    ICarRepository repository;

    ModelMapper mapper;

    GetCarByIdUseCase getCarByIdUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        getCarByIdUseCase = new GetCarByIdUseCase(repository,mapper);
    }

    @Test
    void getCarById(){

        var car = Mono.just(new Car(
                "Mazda",
                "White",
                2019,
                "CBD186"
        ));

        Mockito.when(repository.findById(ArgumentMatchers.anyString())).thenReturn(car);

        var response = getCarByIdUseCase.apply("");

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(repository).findById("");

    }

    @Test
    @DisplayName("getCarById_fail")
    void getCarByIdNotValid(){

        Mockito.when(repository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.empty());

        var response = getCarByIdUseCase.apply("");

        StepVerifier.create(response)
                .expectError(Throwable.class);

        Mockito.verify(repository).findById("");

    }

}