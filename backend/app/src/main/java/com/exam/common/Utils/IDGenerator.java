package com.exam.common.Utils;

import java.util.Random;

/**
 * ID生成器封装
 */
public class IDGenerator {

    private static final IdWorker idWorker = new IdWorker(0, 0);

    /**
     * 字符串类型ID
     *
     * @return
     */
    public static String StringID() {
        long longID = idWorker.nextId();
        String stringID = Long.toString(longID);
        return stringID;
    }

    /**
     * long类型ID
     *
     * @return
     */
    public static long LongID() {
        long longID = idWorker.nextId();
        return longID;
    }

    /**
     * 六位验证码
     *
     * @return
     */
    public static String authCode() {
        //生成6位随机数字
        int anInt = new Random().nextInt(999999);
        String authCode = String.valueOf(anInt);
        return authCode;
    }
}
