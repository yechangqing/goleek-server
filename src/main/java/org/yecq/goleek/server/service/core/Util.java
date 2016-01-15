package org.yecq.goleek.server.service.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author yecq
 */
public final class Util {

    public static String getWeekDay(Calendar c) {
        String[] weeks = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        int w = c.get(Calendar.DAY_OF_WEEK);
        return weeks[w - 1];
    }

    // 获取几天以后的日期，跳开双休日
    public static String getDateAfter(String from, int k) {
        try {
            Set set = new HashSet();
            set.add("周六");
            set.add("周日");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            if (from != null) {
                c.setTime(format.parse(from));
            }
            int count = 0;
            while (count < k) {
                // 获取下一天
                c.add(Calendar.DATE, 1);
                String week = getWeekDay(c);
                if (!set.contains(week)) {
                    count++;
                }
            }
            return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DATE);
        } catch (ParseException e) {
            throw new IllegalArgumentException("日期格式错误");
        }
    }

    // 日期比较
    public static int compareDate(String d1, String d2) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c1 = Calendar.getInstance();
            c1.setTime(format.parse(d1));
            Calendar c2 = Calendar.getInstance();
            c2.setTime(format.parse(d2));
            return c1.compareTo(c2);
        } catch (ParseException e) {
            throw new IllegalArgumentException("日期格式错误");
        }
    }

    // 获取今天的日期
    public static String getTodayStr() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DATE);
    }
}
