package com.smapley.powerwork.utils;


import org.xutils.common.util.LogUtil;

import java.sql.Timestamp;

public class Code {

    /**
     * 加密
     */
    public static String enCode(String data, long number) {
        String code = new Timestamp(number).toString();
        code+=code;
        String result = "";
        byte[] bytes1 = data.getBytes();
        byte[] bytes2 = code.getBytes();
        for (int i = 0; i < bytes1.length; i++) {
            bytes1[i] += bytes2[i];
            result += bytes1[i] + ",";
        }
        return result;
    }

    /**
     * 解密
     */
    public static String doCode(String data, long number) {
        String code = new Timestamp(number).toString();
        code+=code;
        LogUtil.d("---"+number);
        LogUtil.d("---"+code);
        LogUtil.d("---"+data);
        String[] datas = data.split(",");
        byte[] bytes1 = new byte[datas.length];
        byte[] bytes2 = code.getBytes();
        for (int i = 0; i < bytes1.length; i++) {
            bytes1[i] = (byte) (Integer.parseInt(datas[i]) - bytes2[i]);
        }
        LogUtil.d("---"+new String(bytes1));

        return new String(bytes1);
    }

}

