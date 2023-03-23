package com.diegob.carsapi.usecases;

import com.diegob.carsapi.domain.collection.Car;
import com.diegob.carsapi.domain.dto.CarDTO;
import com.diegob.carsapi.publisher.CarPublisher;
import com.diegob.carsapi.repository.ICarRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RentCarUseCaseTest {

    @Mock
    ICarRepository repository;

    ModelMapper mapper;

    RentCarUseCase rentCarUseCase;

    CarPublisher carPublisher;

    @Mock
    RabbitTemplate rabbitTemplate;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        carPublisher = new CarPublisher(rabbitTemplate,new ObjectMapper());
        rentCarUseCase = new RentCarUseCase(repository,mapper,carPublisher);
    }

    @Test
    void rentCar(){

        var car = new Car(
                "Mazda",
                "White",
                2019,
                "CBD186"
        );

        Mockito.when(repository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.just(car));
        Mockito.when(repository.save(ArgumentMatchers.any(Car.class))).thenReturn(Mono.just(mapper.map(car, Car.class)));
        //Mockito.when(rabbitTemplate.convertAndSend(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(),ArgumentMatchers.anyString()));

        var response = rentCarUseCase.rent(car.getId(), "");

        StepVerifier.create(response)
                //.expectNext(mapper.map(car,CarDTO.class))
                .expectNextMatches(CarDTO::isRented)
                .verifyComplete();

        Mockito.verify(repository).findById(ArgumentMatchers.anyString());
        Mockito.verify(repository).save(ArgumentMatchers.any(Car.class));

    }

    @Test
    void rentCarIdNotFound_fail(){

        Mockito.when(repository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.empty());

        var response = rentCarUseCase.rent("","");

        StepVerifier.create(response)
                .expectError(Throwable.class);

        Mockito.verify(repository).findById(ArgumentMatchers.anyString());

    }

}