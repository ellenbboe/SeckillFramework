package sf.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {
    public final static String QUEUE_NAME = "sf_queue";
//    public final static String QUEUE_EXCHANGE = "spring-topic-exchange";
//    public final static String QUEUE_ROUTING_KEY = "sf";
    //队列
    @Bean
    public Queue queue(){
        return new Queue(QUEUE_NAME,true);
    }
////交换机
//    @Bean
//    public TopicExchange topicExchange(){
//        return new TopicExchange(QUEUE_EXCHANGE);
//    }
////绑定队列到交换机 with key
//    @Bean
//    public Binding binding(){
//        return BindingBuilder.bind(queue()).to(topicExchange()).with(QUEUE_ROUTING_KEY);
//    }


}
