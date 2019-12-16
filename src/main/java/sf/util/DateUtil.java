package sf.util;

import java.util.Date;

public class DateUtil {
    public static Long getDistanceTimestamp(Date startDate, Date endDate){
        return (endDate.getTime()-startDate.getTime()+1000000)/(1000);
    }
    public static long secoundToSeckill(Date start,Date end)
    {
        Date now = new Date();
        long StartToNow = getDistanceTimestamp(start,now);
        long NowToEnd = getDistanceTimestamp(now,end);
        if(StartToNow>=0 && NowToEnd>=0){
            return 0;
        }else if(StartToNow <0)
        {
            return -StartToNow;
        }else{
            return -1;
        }
    }
}
