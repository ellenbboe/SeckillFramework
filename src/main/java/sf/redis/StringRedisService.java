package sf.redis;

import com.alibaba.druid.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import sf.util.JwtUtil;

import java.util.concurrent.TimeUnit;

@Service
public class StringRedisService {
    private static final Logger logger = LoggerFactory.getLogger(StringRedisService.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public  void setString(String key, String value,long time){
        logger.info("--------------------->[Redis set start]");
        stringRedisTemplate.opsForValue().set(key,value);
        expice(key,time);
    }

    public String getString(String key){
        logger.info("--------------------->[Redis get start]");
        return  stringRedisTemplate.opsForValue().get(key);
    }
    public boolean exits(String key){
        if(stringRedisTemplate.hasKey(key)!=null)
        {
            return !StringUtils.isEmpty(getString(key));
        }
        return false;
    }

    public boolean del(String key)
    {
        if(stringRedisTemplate.hasKey(key)!=null)
        {
            try{
                stringRedisTemplate.delete(key);
                return true;
            }
            catch (Exception e)
            {
                return false;
            }

        }
        return false;
    }

    //设置过期时间 分钟
    public boolean expice(String key,long time)
    {
        try{
            stringRedisTemplate.expire(key,time, TimeUnit.MINUTES);
        }catch (Exception e)
        {
            return false;
        }
        return true;
    }



    //redis For token

    /**
     * 创建refreshToken并存放到redis中
     * @param token
     */
    public void createRefreshTokenAndSave(String token)
    {
        System.out.println("出错了!!!!!!!");
        String newRefreshToken = JwtUtil.CreateRefreshToken(token);
        String key = RedisKey.getRedisKey(RedisKey.REDIS_USER_LOGIN_MODEL,RedisKey.REDIS_USER_LOGIN_Token,token);
        setString(key,newRefreshToken,RedisKey.REDIS_LOGIN_TOKENREFRESH_EXPICETIME);//将refreshToken存放到token中
    }







}
