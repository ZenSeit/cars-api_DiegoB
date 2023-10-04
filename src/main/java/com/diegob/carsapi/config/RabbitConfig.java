package com.diegob.carsapi.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String CARS_QUEUE = "cars-rented.queue.direct";
    public static final String CARS_QUEUE_TOPIC_ONE = "cars-rented.queue.topic.one";
    public static final String CARS_QUEUE_TOPIC_TWO = "cars-rented.queue.topic.two";
    public static final String CARS_QUEUE_TOPIC_THREE = "cars-rented.queue.topic.three";

    public static final String CARS_QUEUE_FANOUT_ONE = "cars-rented.queue.fanout.one";
    public static final String CARS_QUEUE_FANOUT_TWO = "cars-rented.queue.fanout.two";
    public static final String CARS_QUEUE_FANOUT_THREE = "cars-rented.queue.fanout.three";
    public static final String CARS_QUEUE_DIRECT = "cars-rented.queue.direct";
    public static final String ROUTING_KEY = "events.cars.routing.key";
    public static final String EXCHANGE_TOPIC = "cars-exchange-events";
    public static final String EXCHANGE_DIRECT = "cars-exchange-direct-events";
    public static final String EXCHANGE_FANOUT = "cars-exchange-fanout-events";

    public static final String GENERAL_QUEUE = "general.queue";
    public static final String ROUTING_KEY_GENERAL = "events.#";
    public static final String ROUTING_KEY_DIRECT = "events.cars.direct.routing.key";

    public static final String ROUTING_KEY_TOPIC = "events.cars.topic.*";



    @Bean
    public RabbitAdmin rabbitAdmin(RabbitTemplate rabbitTemplate) {
        var admin =  new RabbitAdmin(rabbitTemplate);
        admin.declareExchange(new DirectExchange(EXCHANGE_DIRECT));
        admin.declareExchange(new TopicExchange(EXCHANGE_TOPIC));
        admin.declareExchange(new FanoutExchange(EXCHANGE_FANOUT));
        return admin;
    }

    //Set bean to queue
    @Bean
    public Queue eventsTopicOneQueue(){
        return new Queue(CARS_QUEUE_TOPIC_ONE);
    }

    @Bean
    public Queue eventsTopicTwoQueue(){
        return new Queue(CARS_QUEUE_TOPIC_TWO);
    }

    @Bean
    public Queue eventsTopicThreeQueue(){
        return new Queue(CARS_QUEUE_TOPIC_THREE);
    }



    @Bean
    public Queue eventsQueueDirect(){
        return new Queue(CARS_QUEUE_DIRECT);
    }

    @Bean
    public Queue eventsFanoutOneQueue(){
        return new Queue(CARS_QUEUE_FANOUT_ONE);
    }

    @Bean
    public Queue eventsFanoutTwoQueue(){
        return new Queue(CARS_QUEUE_FANOUT_TWO);
    }

    @Bean
    public Queue eventsFanoutThreeQueue(){
        return new Queue(CARS_QUEUE_FANOUT_THREE);
    }


    //Set bean to exchange
    @Bean
    public TopicExchange eventsExchange(){
        return new TopicExchange(EXCHANGE_TOPIC);
    }

    @Bean
    public DirectExchange eventsExchangeDirect(){
        return new DirectExchange(EXCHANGE_DIRECT);
    }

    @Bean
    public FanoutExchange eventsExchangeFanout(){
        return new FanoutExchange(EXCHANGE_FANOUT);
    }

    @Bean
    public TopicExchange eventsExchangeTopic(){
        return new TopicExchange(EXCHANGE_TOPIC);
    }

    @Bean
    public Binding eventsDirectBinding(){
        return BindingBuilder.bind(this.eventsQueueDirect()).to(this.eventsExchangeDirect()).with(ROUTING_KEY_DIRECT);
    }

    //Set beans related to binding
    @Bean
    public Binding eventsTopicOneBinding() {
        return BindingBuilder.bind(eventsTopicOneQueue()).to(eventsExchangeTopic()).with(ROUTING_KEY_TOPIC);
    }

    @Bean
    public Binding eventsTopicTwoBinding() {
        return BindingBuilder.bind(eventsTopicTwoQueue()).to(eventsExchangeTopic()).with(ROUTING_KEY_TOPIC);
    }

    @Bean
    public Binding eventsTopicThreeBinding() {
        return BindingBuilder.bind(eventsTopicThreeQueue()).to(eventsExchangeTopic()).with(ROUTING_KEY_TOPIC);
    }

    @Bean
    public Binding eventsFanoutOneBinding() {
        return BindingBuilder.bind(eventsFanoutOneQueue()).to(eventsExchangeFanout());
    }

    @Bean
    public Binding eventsFanoutTwoBinding() {
        return BindingBuilder.bind(eventsFanoutTwoQueue()).to(eventsExchangeFanout());
    }

    @Bean
    public Binding eventsFanoutThreeBinding() {
        return BindingBuilder.bind(eventsFanoutThreeQueue()).to(eventsExchangeFanout());
    }


}
