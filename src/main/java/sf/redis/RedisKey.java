package sf.redis;

public class RedisKey {
    //模式:项目+模块(功能)+前缀+特征值
    public static final String REDIS_PROJECT = "SECKILLFRAMEWORK:";

    //模块/功能
    public static final String REDIS_PAGE_MODEL = "PAGE:";
    public static final String REDIS_USER_LOGIN_MODEL = "USER:";

    //前缀
    public static final String REDIS_USER_LOGIN_Token = "UserLoginToken:";
    public static final String REDIS_PAGENAME_SECKILLLIST = "SeckillList";
    public static final String REDIS_USER_PAGEMODEL = "UserPageModel:";
    //redis中的过期时间常数
    //用户token用于刷新的过期时间(分钟)
    public static final long REDIS_LOGIN_TOKENREFRESH_EXPICETIME = 20;

    //页面在redis中的缓存时间(分钟)
    public static final long REDIS_LOGIN_PAGE_EXPICETIME = 1;
    //用户的pagemodel缓存
    public static final long REDIS_USER_PAGEMODEL_EXPICETIME = 1;


    //方法
    public static String getRedisKey(String model,String pre,String value)
    {
        return REDIS_PROJECT+model+pre+value;
    }

}
