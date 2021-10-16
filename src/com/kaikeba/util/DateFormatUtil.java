package com.kaikeba.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 此工具类专门用于日期格式化，用于把Date类型转换为年月日时分秒的格式
 */
public class DateFormatUtil {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

    public static String format(Date date){
        return format.format(date);
    }
}
