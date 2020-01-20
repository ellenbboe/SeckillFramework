package sf.redis;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sf.entity.User;
import sf.service.UserService;
import sf.util.CookieUtil;
import sf.util.UUIDUtil;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public void setObj(String key, Object value,Long time) {
        String s = beanToString(value);
        redisTemplate.opsForValue().set(key,s,time,TimeUnit.SECONDS);
    }

    public void setObj(Class clazz,String key, Object value,Long time,TimeUnit timeUnit) {
        if(clazz == Integer.class||clazz == Long.class)
        {
            redisTemplate.opsForValue().set(key,value,time,timeUnit);
        }else if(clazz == List.class){
            redisTemplate.opsForList().leftPush(key,value);
            expice(key,time,timeUnit);
        }
        else{
            String s = beanToString(value);
            redisTemplate.opsForValue().set(key,s,time,timeUnit);
        }
    }

    public <T> T getObj(String key, Class<T> tClass) {
        if(tClass==List.class)
        {
            return (T) redisTemplate.opsForList().leftPop(key);
        }else{
            Object value = redisTemplate.opsForValue().get(key);
            return stringToBean(value,tClass);
        }

    }

    public void setList(String key, Object value, Long time){
        redisTemplate.opsForList().leftPush(key,value);
        expice(key,time,TimeUnit.SECONDS);
    }

    public Object getList(String key)
    {
        return redisTemplate.opsForList().leftPop(key);
    }

    public boolean expice(String key,long time,TimeUnit timeUnit)
    {
        try{
            redisTemplate.expire(key,time, timeUnit);
        }catch (Exception e)
        {
            return false;
        }
        return true;
    }

    public long decr(String key)
    {
        return redisTemplate.opsForValue().decrement(key);
    }


    public static <T> T stringToBean(Object value, Class<T> clazz) {
        if(value==null||clazz==null){
            return null;
        }
        if(clazz==String.class||clazz==List.class||clazz==Integer.class||clazz==Long.class){
            return (T)value;
        }else {
            String value1 = (String)value;
            return JSON.toJavaObject(JSON.parseObject(value1),clazz);
        }
    }


    public static  <T> String beanToString(T value) {
        if(value==null){
            return null;
        }
        Class <?> clazz = value.getClass();
        if(clazz==String.class){
            return (String)value;
        }else {
            return JSON.toJSONString(value);
        }
    }

    public boolean haveKey(String key)
    {
        if(StringUtils.isEmpty(key))
        {
            return false;
        }
        try{
            return redisTemplate.hasKey(key);
        }catch (Exception e)
        {
            return false;
        }
    }
}
