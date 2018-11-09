package com.juggist.jcore.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author juggist
 * @date 2018/10/31 10:08 AM
 */
public class StringUtil {
    public static String unicodeToCn(String dataStr) {

        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
            start = end;
        }
        return buffer.toString();
    }


    //四舍五入把double转化int整型，0.5进一，小于0.5不进一
    public static int getInt(double number) {
        BigDecimal bd = new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
        return Integer.parseInt(bd.toString());
    }

    //四舍五入把double转化为int类型整数,0.5也舍去,0.51进一
    public static int DoubleFormatInt(Double dou) {
        DecimalFormat df = new DecimalFormat("######0"); //四色五入转换成整数
        return Integer.parseInt(df.format(dou));
    }

    //去掉小数凑整:不管小数是多少，都进一
    public static int ceilInt(double number) {
        return (int) Math.ceil(number);
    }
}
