package sf.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
    public static String md5(String src)
    {
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "9d5b364d";
//使用固定salt进行md5,用户端的加密
    public static String inputPassForm(String inputPass)
    {
        String str = inputPass + salt;
        return md5(str);
    }
//使用随机salt进行md5,服务端的加密
    public static String FormPassToDB(String formpass,String salt)
    {
        String str = formpass + salt;
        return md5(str);
    }

    //将两步操作合二为一,直接变成服务器的密码
    public static String inputPassToDB(String inputpass,String salt)
    {
        return FormPassToDB(inputPassForm(inputpass),salt);
    }
    
}
