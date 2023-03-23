package com.diegob.carsapi.publisher;

import com.diegob.carsapi.config.RabbitConfig;
import com.diegob.carsapi.domain.dto.CarDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CarPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public void publish(CarDTO carDTO, String idDriver) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(new CarEvent(idDriver,carDTO,"rent"));
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY, message);
    }

}
