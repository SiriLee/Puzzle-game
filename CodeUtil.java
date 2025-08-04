package com.siri.tool;

import java.util.Random;

public class CodeUtil {
    //生成验证码
    public static String getVfCode() {
        Random rd = new Random();
        char[] tmpChArr = new char[5];
        for (int i = 0; i < 4; i++) {
            tmpChArr[i] = getRandomLetter();
        }
        tmpChArr[4] = getRandomDigit();
        for (int i = 0; i < 5; i++) {
            int randomIndex = rd.nextInt(5);
            char tmp = tmpChArr[i];
            tmpChArr[i] = tmpChArr[randomIndex];
            tmpChArr[randomIndex] = tmp;
        }
        return new String(tmpChArr);
    }

    //获取随机单个字母字符
    public static char getRandomLetter() {
        Random rd = new Random();
        int code = (rd.nextInt(2) == 1 ? 'A' : 'a') + rd.nextInt('Z' - 'A' + 1);
        return (char) code;
    }

    //获取随机单个数字字符
    public static char getRandomDigit() {
        Random rd = new Random();
        return (char) ('0' + rd.nextInt(10));
    }
}
