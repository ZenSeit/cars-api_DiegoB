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
class DeleteCarUseCaseTest {

    @Mock
    ICarRepository repository;

    DeleteCarUseCase deleteCarUseCase;

    @BeforeEach
    void init(){
        deleteCarUseCase = new DeleteCarUseCase(repository);
    }

    @Test
    void deleteCar(){

        var car = new Car(
                "Mazda",
                "White",
                2019,
                "CBD186"
        );

        Mockito.when(repository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.just(car));

        Mockito.when(repository.delete(ArgumentMatchers.any(Car.class))).thenReturn(Mono.empty());

        var response = deleteCarUseCase.delete("");

        StepVerifier.create(response)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(repository).delete(ArgumentMatchers.any(Car.class));
    }

    @Test
    @DisplayName("deleteCar_Fail")
    void deleteCar_Fail(){

        Mockito.when(repository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.empty());

        var response = deleteCarUseCase.delete("");

        StepVerifier.create(response)
                .expectError(Throwable.class);

        Mockito.verify(repository).findById("");

    }

}