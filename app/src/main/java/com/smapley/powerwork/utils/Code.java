package com.smapley.powerwork.utils;


public class Code {

    public static final String code="5D12F54S1F5D8DF1E215D4D5SD2D5F12DF45DF12";

    public static String enCode(String data){
        String result="";
        byte[] bytes1=data.getBytes();
        byte[] bytes2=code.getBytes();
        for (int i = 0; i < bytes1.length; i++) {
            bytes1[i]+=bytes2[i];
            result+=bytes1[i]+",";
        }
        return result;
    }

    public static String doCode(String data){
        String[] datas=data.split(",");
        byte[] bytes1=new byte[datas.length];
        byte[] bytes2=code.getBytes();
        for (int i = 0; i < bytes1.length; i++) {
            bytes1[i]=(byte) (Integer.parseInt(datas[i])-bytes2[i]);
        }
        return new String(bytes1);
    }

}

