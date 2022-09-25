package br.com.producer.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static br.com.lib.constant.RabbitMQConstant.EXCHANGE_TYPE;
import static br.com.lib.constant.RabbitMQConstant.PRODUCT_QUEUE_NAME;

@Component
public class RabbitMQConfig {
    private AmqpAdmin amqpAdmin;

    public RabbitMQConfig(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    private Queue createQueue(String queueName) {
        return new Queue(queueName, true, false, false);
    }

    private DirectExchange createDirectExchange() {
        return new DirectExchange(EXCHANGE_TYPE);
    }

    private Binding createBinding(Queue queue, DirectExchange directExchange) {
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, directExchange.getName(), queue.getName(), null);
    }

    @PostConstruct
    private void sendToBroker() {
        Queue inventoryQueue = this.createQueue(PRODUCT_QUEUE_NAME);
        DirectExchange directExchange = this.createDirectExchange();
        Binding inventoryBinding = this.createBinding(inventoryQueue, directExchange);

        this.amqpAdmin.declareQueue(inventoryQueue);
        this.amqpAdmin.declareExchange(directExchange);
        this.amqpAdmin.declareBinding(inventoryBinding);
    }
}
