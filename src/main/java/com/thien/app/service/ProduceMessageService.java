package com.thien.app.service;

import com.thien.app.rabbitmq.ConfigRabbitMq;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProduceMessageService {
    private final RabbitTemplate rabbitTemplate;

    public ProduceMessageService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public String produceMessage(String message) {
        rabbitTemplate.convertAndSend(ConfigRabbitMq.EXCHANGE_NAME, "myRoutingKey.messages",
                message);
        return "Message(" + message + ")" + " has been produced.";
    }
}
