package sf.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RedisAopHandler {
    @Autowired
    RedisTemplate redisTemplate;

    @Around("@annotation(redisAop)")
    public Object aroundMethod(ProceedingJoinPoint pjd, RedisAop redisAop) throws Throwable {
        Object[] args = pjd.getArgs();
        String key = redisAop.value();
        for (int i = 0; i < args.length; i++) {
            String replace = "#{" + i + "}";
            key = key.replace(replace, String.valueOf(args[i]));
        }
        key = redisAop.keypre()+key;
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object value = valueOperations.get(key);
        //redis 热点问题
        //服务器多线程并发访问时，可能会有多个线程同时访问为空，都会去执行设置操作，
        if (value == null) {
            //可能有多个线程阻塞在这里，当synchronized执行完成之后。
            synchronized (this){
                value = valueOperations.get(key);
                //每个线程进入后，发现如果已经有值了，就不再执行set操作
                if(value==null){
                    value = pjd.proceed();
                    valueOperations.set(key, value, redisAop.expire(), TimeUnit.SECONDS);
                }
            }
        }
        return value;
    }
}
