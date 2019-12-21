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


    public  void setString(String key, String value){
        logger.info("--------------------->[Redis set start]");
        stringRedisTemplate.opsForValue().set(key,value);
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

    //设置过期时间 天数
    public boolean expice(String key,long time)
    {
        try{
            stringRedisTemplate.expire(key,time, TimeUnit.DAYS);
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
        String newRefreshToken = JwtUtil.CreateRefreshToken(token);
        setString(token,newRefreshToken);//将refreshToken存放到token中
        expice(token,20);
    }





}
