package sf.redis;

public class RedisKey {
    //模式:项目+模块(功能)+前缀+特征值
    public static final String REDIS_PROJECT = "SECKILLFRAMEWORK:";

    //模块/功能
    public static final String REDIS_PAGE_MODEL = "PAGE:";
    public static final String REDIS_USER_LOGIN_MODEL = "USER:";
    public static final String REDIS_MODEL = "MODEL";

    //前缀有冒号  特征值不确定
    public static final String REDIS_USER_LOGIN_Token = "UserLoginToken:";
    public static final String REDIS_USER_PAGEMODEL = "UserPageModel:";
    public static final String REDIS_SECKILL_GOODSMODEL = "SeckillGoodsModel:";
    public static final String REDIS_MODEL_USERMODEL="userModel:";
    public static final String REDIS_MODEL_GOODSMODEL="goodsModel:";
    public static final String REDIS_MODEL_ORDERMODEL="orderModel:";

    //前缀+特征值已确定
    public static final String REDIS_PAGENAME_SECKILLLIST = "SeckillList";



    //redis中的过期时间常数
    //用户token用于刷新的过期时间(秒)
    public static final long REDIS_LOGIN_TOKENREFRESH_EXPICETIME = 20*60;

    //页面在redis中的缓存时间(秒)
    public static final long REDIS_LOGIN_PAGE_EXPICETIME = 10*60;
    //用户的pagemodel缓存
    public static final long REDIS_USER_PAGEMODEL_EXPICETIME = 10*60;
    //Model time
    public static final long REDIS_MODEL_EXPICETIME = 10*60;


    //方法
    public static String getRedisKey(String model,String pre,String value)
    {
        return REDIS_PROJECT+model+pre+value;
    }

}
