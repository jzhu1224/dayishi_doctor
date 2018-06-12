package com.dayishiapp.doctor.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    /***
     * 格式化手机号
     * @param phoneNum 18054697895
     * @return 180 5469 7895
     */
    public static String formatPhoneWithSpace(String phoneNum){
        if(TextUtils.isEmpty(phoneNum)){
            return "";
        }
        if(phoneNum.length() != 11){
            return phoneNum;
        }
        phoneNum = phoneNum.trim();
        StringBuffer formatPhone = new StringBuffer(phoneNum.substring(0,3)).append(" ");
        formatPhone.append(phoneNum.substring(3,7)).append(" ").append(phoneNum.substring(7,11));
        return formatPhone.toString();
    }

    public static String formatPhoneWithLine(String phoneNum) {
        if(TextUtils.isEmpty(phoneNum)){
            return "";
        }
        if(phoneNum.length() != 11){
            return phoneNum;
        }
        phoneNum = phoneNum.trim();
        StringBuffer formatPhone = new StringBuffer(phoneNum.substring(0,3)).append("-");
        formatPhone.append(phoneNum.substring(3,7)).append("-").append(phoneNum.substring(7,11));
        return formatPhone.toString();
    }

    //金额验证
    public static boolean isMoneyNumber(String str) {
        //判断小数点后2位的数字的正则表达式
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    public static final String RULE_AUTHCODE_SIMPLE = "^[0-9]*$";

    /**
     * 简单校验手机号（规则：长度<=15，仅数字）
     *
     * @param mobile
     * @return
     */
    public static boolean isValidMobile(String mobile) {
        if (mobile == null || "".equals(mobile)) {
            return false;
        }
        if (!mobile.matches(RULE_AUTHCODE_SIMPLE)) {
            return false;
        }

        return !(mobile.length() < 11 || mobile.length() > 15);
    }

    private static String getStarSub(String number) {
        String formatStr = "";
        if (number.length() == 11) {
            formatStr = " **** ";
        } else if (number.length() == 12) {
            formatStr = " ***** ";
        } else if (number.length() == 13) {
            formatStr = " ****** ";
        } else if (number.length() == 14) {
            formatStr = " ******* ";
        } else if (number.length() == 15) {
            formatStr = " **** **** ";
        }
        return formatStr;
    }

    /**
     * 只显示前三位+****+后四位
     *
     * @param number
     * @return
     */
    public static String formatMobileSub(String number) {
        if (!isValidMobile(number))
            return number;
        String num1 = number.substring(0, 3);
        String num2 = number.substring(number.length() - 4, number.length());
        String formatStr = num1 + getStarSub(number) + num2;
        return formatStr;
    }

    public static String formatRoomNumber(int room) {
        if (room<10) {
            return "0"+room;
        }
        return String.valueOf(room);
    }
}
