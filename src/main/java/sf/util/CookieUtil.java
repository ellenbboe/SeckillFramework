package sf.util;

import org.springframework.beans.factory.annotation.Autowired;
import sf.entity.User;
import sf.redis.RedisService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static final String  USER_COOKIE_TOKEN_NAME = "token";


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

    public static boolean setCookieValue(HttpServletRequest request,HttpServletResponse response,String cookieName,String value)
    {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null)
        {
            for (Cookie one:cookies
            ) {
                if(one.getName().equals(cookieName))
                {
                    one.setValue(value);
                    one.setPath("/");
                    one.setMaxAge(60*60*24);
                    response.addCookie(one);
                    return true;
                }
            }
        }
        return false;
    }
//del浏览器的Token cookies
    public static boolean delTokenCookies(HttpServletResponse response)
    {
        try {
            Cookie cookie = new Cookie("token", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }
}
