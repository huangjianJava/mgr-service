package com.advance.mgr.common;

import com.advance.mgr.util.DateTimeUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * 日期转换为时间戳
 */
public class TimestampFormatter implements Formatter<Date> {

    @Override
    public String print(Date date, Locale locale) {
        return String.valueOf(date.getTime());
    }

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        long time = NumberUtils.toLong(text);
        if (time > 0)
            return new Date(time);
        else
            return DateTimeUtil.DEFAULT_DATE_MIN;
    }


}
