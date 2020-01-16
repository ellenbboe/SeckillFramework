package sf.redis;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
public class RedisService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public void setObj(String key, Object value,Long time) {
        redisTemplate.opsForValue().set(key,value);
        expice(key,time);
    }

    public Object getObj(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setList(String key, Object value, Long time){
        redisTemplate.opsForList().leftPush(key,value);
        expice(key,time);
    }
    public Object getList(String key)
    {
        return redisTemplate.opsForList().leftPop(key);
    }
    public boolean expice(String key,long time)
    {
        try{
            redisTemplate.expire(key,time, TimeUnit.SECONDS);
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


    public static <T> T stringToBean(String value, Class<T> clazz) {
        if(value==null||value.length()<=0||clazz==null){
            return null;
        }

        if(clazz ==int.class ||clazz==Integer.class){
            return (T)Integer.valueOf(value);
        }
        else if(clazz==long.class||clazz==Long.class){
            return (T)Long.valueOf(value);
        }
        else if(clazz==String.class){
            return (T)value;
        }else {
            return JSON.toJavaObject(JSON.parseObject(value),clazz);
        }
    }


    public static  <T> String beanToString(T value) {

        if(value==null){
            return null;
        }
        Class <?> clazz = value.getClass();
        if(clazz==int.class||clazz==Integer.class){
            return ""+value;
        }
        else if(clazz==long.class||clazz==Long.class){
            return ""+value;
        }
        else if(clazz==String.class){
            return (String)value;
        }else {
            return JSON.toJSONString(value);
        }
    }
}
