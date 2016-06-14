package com.yuen.baselib.utils;

import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by WangXY on 2015/10/8.20:27.
 */
public class PinYinUtils {
    /**
     * 获取汉字串拼音，英文字符不变
     *
     * @param chinese 汉字串
     * @return 汉语拼音
     */
    public static String cn2Spell(String chinese) {
        if (!TextUtils.isEmpty(chinese)) {
            chinese = chinese.trim();
        }
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128 && arr[i] != '﹒') {
                try {
                    pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i],
                            defaultFormat)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(arr[i]);
            }
        }
        return pybf.toString();
    }

    /**
     * 提取每个汉字的首字母
     *
     * @param str
     * @return String
     */
    public static String getPinYinHeadChar(String str) {
        if (!TextUtils.isEmpty(str)) {
            str = str.trim();
        }
        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            // 提取汉字的首字母
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
            break;
        }
        return convert.toUpperCase();
    }
    /**
     * 将一个list<String>转换成拼音
     * @param list
     */
    public static List<String> getPinyinList(List<String> list){
        List<String> pinyinList = new ArrayList<String>();
        for(Iterator<String> i = list.iterator(); i.hasNext();) {
            String str = (String)i.next();
            try {
                String pinyin = getPinYin(str);
                pinyinList.add(pinyin);
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
        }
        return pinyinList;
    }

    /**
     * 将一个中文字符串转换称拼音
     * @param 中文字符串
     * @return
     */
    public static String getPinYin(String zhongwen)
            throws BadHanyuPinyinOutputFormatCombination {

        String zhongWenPinYin = "";
        char[] chars = zhongwen.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            String[] pinYin = PinyinHelper.toHanyuPinyinStringArray(chars[i], getDefaultOutputFormat());
            // 如果为空时，返回自己
            if (pinYin != null) {
                zhongWenPinYin += pinYin[0];
            } else {
                zhongWenPinYin += chars[i];
            }
        }
        return zhongWenPinYin;
    }

    /**
     * 转换格式
     * @return
     */
    private static HanyuPinyinOutputFormat getDefaultOutputFormat() {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);// 大写
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//
        format.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);//
        return format;
    }

}
