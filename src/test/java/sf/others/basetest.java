package sf.others;

import org.apache.shiro.crypto.hash.Md5Hash;
import sf.util.MD5Util;


public class basetest {

    public static void main(String[] args) {

        String credentials = new Md5Hash("e10adc3949ba59abbe56e057f20f883e", "13750882454").toString();
        System.out.println(credentials);
    }
}
