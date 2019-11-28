package com.nathan.ex.util;

import com.nathan.ex.constant.LogLevelConstant;

/**
 * @author nathan.yang
 * @date 2019/11/28
 */
public class Log {

    /**
     * Controlled by VM options: -Djava.log.level
     */
    private static int LogLevel = Integer.parseInt(System.getProperty("java.log.level", "5"));

    /**
     * Log the method invoked stack trace
     * */
    public static void trace() {
        if (LogLevelConstant.LOG_TRACE >= LogLevel) {
            (new Exception()).printStackTrace();
        }
    }

    public static void e(String msg, Object... args) {
        if (LogLevelConstant.LOG_ERROR >= LogLevel) {
            System.out.println(format(msg, args));
        }
    }

    public static void e(String tag, String msg, Object... args) {
        if (LogLevelConstant.LOG_ERROR >= LogLevel) {
            System.out.println(format(tag, msg, args));
        }
    }

    public static void d(String msg, Object... args) {
        if (LogLevelConstant.LOG_DEBUG >= LogLevel) {
            System.out.println(format(msg, args));
        }
    }

    public static void d(String tag, String msg, Object... args) {
        if (LogLevelConstant.LOG_DEBUG >= LogLevel) {
            System.out.println(format(tag, msg, args));
        }
    }

    public static void i(String msg, Object... args) {
        if (LogLevelConstant.LOG_INFO >= LogLevel) {
            System.out.println(format(msg, args));
        }
    }

    public static void i(String tag, String msg, Object... args) {
        if (LogLevelConstant.LOG_INFO >= LogLevel) {
            System.out.println(format(tag, msg, args));
        }
    }

    private static String format(String tag, String format, Object... args) {
        if (null == format || "" .equals(format)) {
            return "";
        }

        String[] array = format.split("\\{\\s*}");
        StringBuilder builder = new StringBuilder();
        builder.append(tag);
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if ((args != null) && (i < args.length) && (args[i] != null)) {
                builder.append(args[i].toString());
            }
        }
        return builder.toString();
    }

    private static String format(String format, Object... args) {
        if (null == format || "" .equals(format)) {
            return "";
        }

        String[] array = format.split("\\{\\s*}");
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if ((args != null) && (i < args.length) && (args[i] != null)) {
                builder.append(args[i].toString());
            }
        }
        return builder.toString();
    }

}
