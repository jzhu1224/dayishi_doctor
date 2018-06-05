package com.chairoad.framework.util;

import java.text.DecimalFormat;

/**
 * Created by yanxin on 18/3/29.
 */

public class MathUtil {

    /**
     * 保留decimal小数 小数不够自动补0
     * @param num
     * @param decimal
     * @return
     */
    public static String decimalFormat(int num,int decimal) {

        double d = Math.pow(10.0,decimal);

        double numD = num/d;

        String pattern = "#0.";
        for(int i=0;i<decimal;i++) {
            pattern += "0";
        }

        DecimalFormat df = new DecimalFormat(pattern);

        return df.format(numD);
    }

    /**
     * 保留decimal小数 小数不够自动补0
     * @param num
     * @param decimal
     * @return
     */
    public static String decimalFormat(double num,int decimal) {

        String pattern = "#0.";
        for(int i=0;i<decimal;i++) {
            pattern += "0";
        }

        DecimalFormat df = new DecimalFormat(pattern);

        return df.format(num);
    }

    /**
     * 保留decimal小数小数 自动去掉末尾的0
     * @param num
     * @param decimal
     * @return
     */
    public static String decimalFormat1(int num,int decimal) {

        double d = Math.pow(10.0,decimal);

        double numD = num/d;

        String pattern = "#0.";
        for(int i=0;i<decimal;i++) {
            pattern += "#";
        }

        DecimalFormat df = new DecimalFormat(pattern);

        return df.format(numD);
    }

    /**
     * 保留decimal小数小数 自动去掉末尾的0
     * @param num
     * @param decimal
     * @return
     */
    public static String decimalFormat1(double num,int decimal) {

        String pattern = "#0.";
        for(int i=0;i<decimal;i++) {
            pattern += "#";
        }

        DecimalFormat df = new DecimalFormat(pattern);

        return df.format(num);
    }

}
