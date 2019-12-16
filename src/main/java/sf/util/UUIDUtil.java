package sf.util;

import java.util.UUID;

public class UUIDUtil {
    public static  String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static String snowFlake()
    {
        SnowFlake snowFlake = new SnowFlake(1,1);
        return String.valueOf(snowFlake.nextId());
    }


}
