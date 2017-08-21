package com.arm.mbed.cloud.sdk.common;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.joda.time.DateTime;

import com.arm.mbed.cloud.sdk.annotations.Internal;
import com.arm.mbed.cloud.sdk.annotations.Preamble;

@Preamble(description = "Utilities for APIs")
@Internal
public class TranslationUtils {

    public static Date toDate(DateTime date) {
        return (date == null) ? null : date.toDate();
    }

    public static long toTimeStamp(Long time) {
        return (time == null) ? 0 : time.longValue();
    }

    public static long toLong(Long longE) {
        return (longE == null) ? 0 : longE.longValue();
    }

    public static int toInt(Integer integer) {
        return toInt(integer, 0);
    }

    public static int toInt(Integer integer, int defaultV) {
        return (integer == null) ? defaultV : integer.intValue();
    }

    public static boolean toBool(Boolean bool, boolean defaultB) {
        return (bool == null) ? defaultB : bool.booleanValue();
    }

    public static Date convertTimestamp(String timestamp) throws Exception {
        return convertTimestamp(timestamp, DateFormat.getDateTimeInstance());
    }

    public static URL toUrl(String url) {
        try {
            return (url == null) ? null : new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toString(URL url) {
        return (url == null) ? null : url.toString();
    }

    @SuppressWarnings("boxing")
    public static Integer convertToInteger(String value, Integer defaultV) {
        if (value == null) {
            return defaultV;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultV;
        }
    }

    public static Date convertTimestamp(String timestamp, DateFormat format) throws Exception {
        if (timestamp == null || timestamp.isEmpty() || format == null) {
            return null;
        }
        try {
            return format.parse(timestamp);
        } catch (ParseException e) {
            throw new Exception("Error occurred when parsing timestamp.", e);
        }
    }
}
