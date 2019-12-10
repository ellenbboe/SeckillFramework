package sf.util;

import org.springframework.beans.factory.annotation.Autowired;
import sf.entity.User;
import sf.redis.RedisService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static final String  USER_COOKIE_TOKEN_NAME = "userToken";

    //将信息添加到cookie中
    public static Cookie addCookie(String key,String value)
    {
        Cookie cookie = new Cookie(key,value);
        cookie.setMaxAge(3600*24*2);
        cookie.setPath("/");
        return cookie;
    }

    //得到指定cookiename的value值
    public static String getCookieValue(HttpServletRequest request,String cookieName)
    {
        Cookie[] cookies = request.getCookies();
        for (Cookie one:cookies
             ) {
            if(one.getName().equals(cookieName))
            {
                return one.getValue();
            }
        }
        return null;
    }

}
