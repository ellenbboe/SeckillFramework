package sf.util;

import java.util.regex.Pattern;

public class ValidatorUtil {
    //手机登录验证
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    public static boolean isMobile(String mobile)
    {
        if(mobile==null)
        {
            return false;
        }
        return Pattern.matches(REGEX_MOBILE,mobile);
    }

}