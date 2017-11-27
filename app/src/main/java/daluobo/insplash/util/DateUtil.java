package daluobo.insplash.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by daluobo on 2017/11/26.
 */

public class DateUtil {
    /**
     *
     * 将格林威治时间字符串转换为yyyy-MM-dd HH:mm字符串，JDK1.7以上版本支持该方法
     *
     * @param s
     * @return
     */
    public static String GmtFormat(String s) {
        String str = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = sd.parse(s);
            str = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
        return str;
    }
}
