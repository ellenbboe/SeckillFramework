package sf.redis;

import sf.util.Constants;

public class RedisKey {
    //模式:项目+模块(功能)+前缀+特征值
    public static final String REDIS_PROJECT = "SECKILLFRAMEWORK:";

    //模块/功能
    public static final String REDIS_PAGE_MODEL = "PAGE:";
    public static final String REDIS_USER_LOGIN_MODEL = "USER:";
    public static final String REDIS_MODEL = "MODEL:";
    public static final String REDIS_GOODS = "GOODS:";

    //前缀有冒号  特征值不确定
    public static final String REDIS_USER_LOGIN_Token = "UserLoginToken:";
    public static final String REDIS_USER_PAGEMODEL = "UserPageModel:";
    public static final String REDIS_SECKILL_GOODSMODEL = "SeckillGoodsModel:";
    public static final String REDIS_MODEL_USERMODEL="userModel:";
    public static final String REDIS_MODEL_GOODSMODEL="goodsModel:";
    public static final String REDIS_MODEL_ORDERMODEL="orderModel:";
    public static final String REDIS_GOODS_STOCK = "goodsStock:";
    public static final String REDIS_GOODS_RANDOM_PATH="randomPath:";

    //前缀+特征值已确定
    public static final String REDIS_PAGENAME_GOODSlIST = "GoodsList";//属于页面
    public static final String REDIS_GOODS_GOODSLIST = "GoodsList";//属于对象

    //redis中的过期时间常数
    //用户token用于刷新的过期时间(秒)
    public static final long REDIS_LOGIN_TOKENREFRESH_EXPICETIME = 20*60;

    //页面在redis中的缓存时间(秒)
    public static final long REDIS_LOGIN_PAGE_EXPICETIME = 10*60;
    //用户的pagemodel缓存
    public static final long REDIS_USER_PAGEMODEL_EXPICETIME = 10*60;
    //Model time
    public static final long REDIS_MODEL_EXPICETIME = 10*60;
    //goodslist
    public static final long REDIS_GOODS_GOODSLISTEXPICETIME = 10*60;
    //goods stock
    public static final long REDIS_GOODS_GOODSSTOCKXPICETIME = 10*60;
    //random seckill path
    public static final long REDIS_GOODS_RANDOM_PATHEXPICETIME = 5*60;



    //方法
    public static String getRedisKey(String model,String pre,String value)
    {
        return REDIS_PROJECT+model+pre+value;
    }
    /**
     * 生成key
     * @param clazzName
     * @param methodName
     * @param args
     * @return
     */
    public static String genKey(String clazzName, String methodName, Object[] args) {
        StringBuilder sb = new StringBuilder(clazzName);
        sb.append(Constants.DELIMITER);
        sb.append(methodName);
        sb.append(Constants.DELIMITER);
        for (Object obj : args) {
            sb.append(obj.toString());
            sb.append(Constants.DELIMITER);
        }
        sb.delete(sb.length()-1,sb.length());
        return sb.toString();
    }
    
}
