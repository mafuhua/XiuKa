package com.yuen.baselib.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 日志工具类
 */
public class MyLogUtil {
    /**
     * 日志总开关
     */
    private static boolean isLogEnable = true;
    /**
     * 日志写入文件开关
     */
    private static boolean isFileEnable = true;
    /**
     * Verbose日志信息开关
     */
    private static boolean isVerboseEnable = true;
    /**
     * Debug日志信息开关
     */
    private static boolean isDebugEnable = true;
    /**
     * Info日志信息开关
     */
    private static boolean isInfoEnable = true;
    /**
     * Warn日志信息开关
     */
    private static boolean isWarnEnable = true;
    /**
     * Error日志信息开关
     */
    private static boolean isErrorEnable = true;
    /**
     * 日志文件名称
     */
    private static final String MYLOG_FILEName = "Log.txt";
    /**
     * 日志内容格式
     */
    private static final SimpleDateFormat MYLOG_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    /**
     * 日志文件名时间格式
     */
    private static final SimpleDateFormat MYLOG_FILE_SDF = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    /**
     * 输出日志
     *
     * @param tag     标签
     * @param message 日志内容
     */
    public static void v(String tag, String message) {
        v(tag, message, null);
    }

    /**
     * 输出日志
     *
     * @param tag     标签
     * @param message 日志内容
     * @param tr      错误信息
     */
    public static void v(String tag, String message, Throwable tr) {
        if (isVerboseEnable()) {
            if (null != tr) {
                Log.v(tag, message, tr);
            } else {
                Log.v(tag, message);
            }
            if (isFileEnable) {
                writeLogtoFile("v", tag, message);
            }
        }
    }

    /**
     * 输出日志
     *
     * @param tag     标签
     * @param message 日志内容
     */
    public static void d(String tag, String message) {
        d(tag, message, null);
    }

    /**
     * 输出日志
     *
     * @param tag     标签
     * @param message 日志内容
     * @param tr      错误信息
     */
    public static void d(String tag, String message, Throwable tr) {
        if (isDebugEnable()) {
            if (null != tr) {
                Log.d(tag, message, tr);
            } else {
                Log.d(tag, message);
            }
            if (isFileEnable) {
                writeLogtoFile("d", tag, message);
            }
        }
    }

    /**
     * 输出日志
     *
     * @param tag     标签
     * @param message 消息内容
     */
    public static void i(String tag, String message) {
        i(tag, message, null);
    }

    /**
     * 输出日志
     *
     * @param tag     标签
     * @param message 日志内容
     * @param tr      异常出错信息
     */
    public static void i(String tag, String message, Throwable tr) {
        if (isInfoEnable()) {
            if (null != tr) {
                Log.i(tag, message, tr);
            } else {
                Log.i(tag, message);
            }
            if (isFileEnable) {
                writeLogtoFile("i", tag, message);
            }
        }
    }

    /**
     * 输出日志
     *
     * @param tag     标签
     * @param message 日志内容
     */
    public static void w(String tag, String message) {
        w(tag, message, null);
    }

    public static void w(String tag, String message, Throwable tr) {
        if (isWarnEnable()) {
            if (null != tr) {
                Log.w(tag, message, tr);
            } else {
                Log.w(tag, message);
            }
            if (isFileEnable) {
                writeLogtoFile("w", tag, message);
            }
        }
    }

    /**
     * 输出日志
     *
     * @param tag     标签
     * @param message 日志内容
     */
    public static void e(String tag, String message) {
        e(tag, message, null);
    }

    /**
     * 输出日志
     *
     * @param tag     标签
     * @param message 日志内容
     * @param tr      异常内容
     */
    public static void e(String tag, String message, Throwable tr) {
        if (isErrorEnable()) {
            if (null != tr) {
                Log.e(tag, message, tr);
            } else {
                Log.e(tag, message);
            }
            if (isFileEnable) {
                writeLogtoFile("e", tag, message);
            }
        }
    }

    /**
     * 日志开关是否开启
     *
     * @return 日志开关是否开启
     */
    private static boolean isVerboseEnable() {
        return isLogEnable && isVerboseEnable;
    }

    /**
     * 日志开关是否开启
     *
     * @return 日志开关是否开启
     */
    private static boolean isDebugEnable() {
        return isLogEnable && isDebugEnable;
    }

    /**
     * 日志开关是否开启
     *
     * @return 日志开关是否开启
     */
    private static boolean isInfoEnable() {
        return isLogEnable && isInfoEnable;
    }

    /**
     * 日志开关是否开启
     *
     * @return 日志开关是否开启
     */
    private static boolean isWarnEnable() {
        return isLogEnable && isWarnEnable;
    }

    /**
     * 日志开关是否开启
     *
     * @return 日志开关是否开启
     */
    private static boolean isErrorEnable() {
        return isLogEnable && isErrorEnable;
    }

    /**
     * 将日志写入文件中
     *
     * @param mylogtype 日志类型
     * @param tag       标签
     * @param text      日志内容
     */
    private static void writeLogtoFile(String mylogtype, String tag, String text) {
     /*   Date nowtime = new Date();
        String needWriteFiel = MYLOG_FILE_SDF.format(nowtime);
        String needWriteMessage = MYLOG_SDF.format(nowtime) + "    " + mylogtype + "    " + tag + "    " + text;
        File file = new File(ProjectConfig.LOGCAT_DIR, needWriteFiel + MYLOG_FILEName);
        try {
            FileWriter filerWriter = new FileWriter(file, true);
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
