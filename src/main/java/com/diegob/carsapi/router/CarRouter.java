package com.diegob.carsapi.router;

import com.diegob.carsapi.domain.DriverDTO;
import com.diegob.carsapi.domain.dto.CarDTO;
import com.diegob.carsapi.usecases.*;
import com.diegob.carsapi.usecases.interfaces.SaveCar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CarRouter {

    private WebClient driverAPI;

    public CarRouter(){
        driverAPI = WebClient.create("http://localhost:8081");
    }

    @Bean
    public RouterFunction<ServerResponse> getAllCar(GetAllCarsUseCase getAllCarsUseCase){
        return route(GET("/cars"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllCarsUseCase.get(), CarDTO.class))
                        .onErrorResume(throwable -> ServerResponse.noContent().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> getCarById(GetCarByIdUseCase getCarByIdUseCase){
        return route(GET("/cars/{id}"),
                request -> getCarByIdUseCase.apply(request.pathVariable("id"))
                        .flatMap(carDTO -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(carDTO))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage())));
    }

    @Bean
    public RouterFunction<ServerResponse> saveCar(SaveCarUseCase saveCarUseCase){
        return route(POST("/cars").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(CarDTO.class)
                        .flatMap(carDTO -> saveCarUseCase.save(carDTO)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))

                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }

    @Bean
    public RouterFunction<ServerResponse> updateCar(UpdateCarUseCase updateCarUseCase){
        return route(PUT("/cars/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(CarDTO.class)
                        .flatMap(studentDTO -> updateCarUseCase.update(request.pathVariable("id"),studentDTO)
                                .flatMap(result -> ServerResponse.status(200)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))

                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))));
    }

    @Bean
    public RouterFunction<ServerResponse> deleteCar(DeleteCarUseCase deleteCarUseCase){
        return route(DELETE("/cars/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request ->  deleteCarUseCase.delete(request.pathVariable("id"))
                        .flatMap(result -> ServerResponse.status(204)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result))

                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage())));
    }

    @Bean
    public RouterFunction<ServerResponse> rentACar(RentCarUseCase rentCarUseCase){
        return route(POST("/cars/{id}/rent/{idDriver}/{type}"),
                request ->
                        driverAPI.get()
                                .uri("/drivers/"+request.pathVariable("idDriver"))
                                .retrieve()
                                .bodyToMono(DriverDTO.class)
                                .flatMap(driverDTO -> rentCarUseCase
                                        .rent(request.pathVariable("id"), driverDTO.getId(),request.pathVariable("type"))
                                        .flatMap(bookDTO -> ServerResponse.ok()
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .bodyValue(bookDTO))
                                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.CONFLICT)
                                                .bodyValue(throwable.getMessage())))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                                        .bodyValue(throwable.getMessage())));
        //.onErrorResume(throwable -> ServerResponse.badRequest().bodyValue(StudentDTO.class)));

    }
}
