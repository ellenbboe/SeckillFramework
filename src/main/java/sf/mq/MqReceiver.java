package sf.mq;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sf.redis.RedisService;
import sf.service.OrderService;

@Service
@Slf4j
public class MqReceiver {
    @Autowired
    OrderService orderService;
    @RabbitListener(queues = MqConfig.QUEUE_NAME)
    public void revc(String message){
        OrdMessage ordmessage = RedisService.stringToBean(message,OrdMessage.class);
        log.info("recv");
        orderService.CreateOrderByGoodsAndUserID(ordmessage.getUserId(),ordmessage.getGoodsId());
    }

}
