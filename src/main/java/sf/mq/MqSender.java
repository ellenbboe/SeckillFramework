package sf.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sf.redis.RedisService;

@Service
public class MqSender {

    @Autowired
    AmqpTemplate amqpTemplate;

    public void send(OrdMessage message){
        String msg = RedisService.beanToString(message);
        amqpTemplate.convertAndSend(MqConfig.QUEUE_NAME,msg);
    }

}
