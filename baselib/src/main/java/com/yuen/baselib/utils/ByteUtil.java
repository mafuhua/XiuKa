package com.yuen.baselib.utils;

/**
 * 字节操作工具类
 * Created by Bassam on 15/6/25.
 */
public class ByteUtil {
    /**
     * 将byte数组变成十六进制格式的字符串输出
     *
     * @param b byte数组
     * @return String
     */
    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        if (b == null) return null;
        for (byte aB : b) {
            String hex = Integer.toHexString(aB & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex).append(" ");
        }
        return sb.toString();
    }

    /**
     * 解析int类型数据
     *
     * @param data      byte数组
     * @param pos       开始位置
     * @param len       需要解析的长度
     * @param bigEndian 是否是大端方式
     * @return int 数组
     */
    public static int parseInt(byte[] data, int pos, int len, boolean bigEndian) {
        int ret = 0;
        for (int i = 0; i < len; i++) {
            ret += ((data[pos + i] & 0xff)) << ((bigEndian ? i : len - 1 - i) * 8);
        }
        return ret;
    }

    /**
     * 解析字类型数据
     *
     * @param data      待解析的字数据（字节数组）
     * @param pos       开始位置
     * @param bigEndian 是否是大端方式
     * @return int
     */
    public static int parseIntForWord(byte[] data, int pos, boolean bigEndian) {
        int ret = 0;
        if (data != null && data.length > (pos + 2)) {
            if (bigEndian) {
                ret += ((data[pos] & 0xff));
                ret += ((data[pos + 1]) << 8);
            } else {
                ret += ((data[pos + 1] & 0xff));
                ret += ((data[pos]) << 8);
            }
        }
        return ret;
    }

    /**
     * 解析双字类型数据
     *
     * @param data      待解析的字数据（字节数组）
     * @param pos       开始位置
     * @param bigEndian 是否是大端方式
     * @return int
     */
    public static int parseIntForDWord(byte[] data, int pos, boolean bigEndian) {
        int ret = 0;
        if (data != null && data.length > (pos + 4)) {

            if (bigEndian) {
                ret += ((data[pos] & 0xff));
                ret += ((data[pos + 1] & 0xff) << 8);
                ret += ((data[pos + 2] & 0xff) << 16);
                ret += ((data[pos + 3]) << 24);
            } else {
                ret += ((data[pos]) << 24);
                ret += ((data[pos + 1] & 0xff) << 16);
                ret += ((data[pos + 2] & 0xff) << 8);
                ret += ((data[pos + 3] & 0xff));
            }
        }
        return ret;
    }
}
