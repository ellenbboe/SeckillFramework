package sf.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sf.entity.User;
import sf.service.UserService;
import sf.util.CookieUtil;
import sf.util.UUIDUtil;

import javax.servlet.http.HttpServletResponse;
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


}
