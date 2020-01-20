package sf.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import sf.cache.RedisCache;
import sf.cache.RedisCacheEvict;
import sf.redis.RedisKey;
import sf.redis.RedisService;
import sf.util.Constants;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class RedisAspect {


    @Autowired
    private RedisService redisService;

    //先查询缓存,不存在就查询数据库,并生成缓存
    @Around("@annotation(redisCache)")
    public Object cache(ProceedingJoinPoint jp,RedisCache redisCache){
        // 将类名，方法名，注解中的key值，参数名称与参数值 作为redis存储的键
        log.info("aop cache");
        String clazzName = jp.getTarget().getClass().getName();
        String methodName = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        // 根据类名，方法名和参数生成key
        String key = RedisKey.genKey(clazzName, methodName, args);
        log.info(key);
        long cacheTime = redisCache.Time();
        TimeUnit timeUnit = redisCache.TIME_UNIT();
        Object result = CacheByRedisKey(jp, key, cacheTime, timeUnit);
        return result;
    }

    //清除缓存
    @Around("@annotation(redisCacheEvict)")
    public Object cache_evict(ProceedingJoinPoint jp, RedisCacheEvict redisCacheEvict){
        return null;
    }

    private Object CacheByRedisKey(ProceedingJoinPoint proceedingJoinPoint, String redisKey, long cacheTime, TimeUnit timeUnit) {
        // 从redis里面读取key为rediskey的值，如果不存在那么就走数据库，如果存在就将缓存中内容返回
        Method me = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        Class modelType = me.getAnnotation(RedisCache.class).TYPE();
        try {
            if (redisService.haveKey(redisKey)) {
                log.info("aop:通过缓存获取数据");
                Object object =  redisService.getObj(redisKey, modelType);
                return object;
            } else {
                //如果缓存中没有数据，则执行方法，查询数据库，dbResult是请求方法返回的信息
                // 我将注解放在service层上，并且service统一了返回信息格式
                Object dbResult = proceedingJoinPoint.proceed();
                if(modelType==List.class||modelType==Integer.class||modelType==Long.class)
                {
                    redisService.setObj(modelType,redisKey, dbResult, cacheTime, timeUnit);
                    return RedisService.stringToBean(dbResult,modelType);

                }else{
                    String value = RedisService.beanToString(dbResult);
                    // 要将返回信息和实体类都实现序列化的接口
                    redisService.setObj(modelType,redisKey, value, cacheTime, timeUnit);
                    return RedisService.stringToBean(value,modelType);
                }
//                String value = RedisService.beanToString(dbResult);
//                // 要将返回信息和实体类都实现序列化的接口
//                redisService.setObj(modelType,redisKey, value, cacheTime, timeUnit);
//                return RedisService.stringToBean(value,modelType);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }


}
