package sf.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import sf.redis.RedisKey;

import java.util.Date;

public class JwtUtil {

    // 生成签名是所使用的秘钥
    private static final String SECRET = "find1you";
    // token在后端的过期时间 24 小时
    private static final long EXPIRE_TIME = 60 * 24 * 60 * 1000;
    //refresh Token 刷新token的refreshtoken 20天
    private static  final long REFRESH_TIME = 60 * 24 * 60 * 1000 * RedisKey.REDIS_LOGIN_TOKENREFRESH_EXPICETIME;


    /**
     * 生成 token
     */
    public static String createToken(String username) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        // 附带username信息
        System.out.println("createToken");
        return JWT.create()
                .withClaim("username", username)
                //到期时间
                .withClaim("createTime",new Date())
                .withExpiresAt(date)
                //创建一个新的JWT，并使用给定的算法进行标记
                .sign(algorithm);
    }

    /**
     * 生成 refreshToken
     */
    public static String CreateRefreshToken(String token)
    {
        DecodedJWT jwt = JWT.decode(token);
        String username = jwt.getClaim("username").asString();
        Date date  =  jwt.getClaim("createTime").asDate();
        Date refreshDate = new Date(date.getTime() + REFRESH_TIME);
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.create()
                .withClaim("username",username)
                .withClaim("createTime",date)
                .withExpiresAt(refreshDate)
                //创建一个新的JWT，并使用给定的算法进行标记
                .sign(algorithm);
    }

    /**
     * 是否可以刷新过期token
     */
    public static boolean canTokenRefresh(String token,String refreshToken)
    {
        Date tokenDate = getCreateTime(token);
        Date RefreshDate = getCreateTime(refreshToken);
        return tokenDate.getTime()==RefreshDate.getTime();
    }

    /**
     * 校验 token 是否正确
     */
    public static boolean verify(String token, String username) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            //在token中附带了username信息
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            //验证 token
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
    /**
     *
     * 校验token是否过期
     * @param token
     * @return
     */
    public static boolean isTokenExpired(String token){
        Date createTime = getCreateTime(token);
        if(createTime==null)
        {
            return true;
        }
        Date EndTime = new Date(createTime.getTime() + EXPIRE_TIME);
        return EndTime.before(new Date());
    }
    /**
     * 获得token中的username信息，无需secret解密也能获得
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    /**
     * 获得token中的createTime信息，无需secret解密也能获得
     */
    public static Date getCreateTime(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("createTime").asDate();
        } catch (JWTDecodeException e) {
            return null;
        }
    }



}
