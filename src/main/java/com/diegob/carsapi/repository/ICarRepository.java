package com.diegob.carsapi.repository;

import com.diegob.carsapi.domain.collection.Car;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICarRepository extends ReactiveMongoRepository<Car,String> {
}
