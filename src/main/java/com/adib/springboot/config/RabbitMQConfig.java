package com.adib.springboot.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.name}")
    private String queue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Value("${rabbitmq.queue.json.name}")
    private String jsonQueueName;

    @Value(("${rabbitmq.routing.json.key}"))
    private String jsonRoutingKey;

    //spring bean for rabbitmq queue
    @Bean
    public Queue queue()
    {
        return new Queue(queue);
    }

    //spring bean for rabbitmq exchange
    @Bean
    public TopicExchange topicExchange()
    {
        return new TopicExchange(exchange);
    }

    //Binding between queue and exchange using routing key
    @Bean
    public Binding binding()
    {
        return BindingBuilder.bind(queue())
                .to(topicExchange())
                .with(routingKey);
    }

    //spring bean for json queue
    @Bean
    public Queue jsonQueue()
    {
        return new Queue(jsonQueueName);
    }

    //binding between json queue and exchange using routing key
    @Bean
    public Binding jsonBinding()
    {
        return BindingBuilder.bind(jsonQueue())
                .to(topicExchange())
                .with(jsonRoutingKey);
    }

    @Bean
    public MessageConverter converter()
    {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory)
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
