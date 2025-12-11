package com.luomor.pcsms.provider.config;

public class SmsBanner {
    private static final String BANNER =
    "luomor-pcsms";
    /** 初始化配置文件时打印banner*/
    public static void PrintBanner(String version) {
        System.out.println(BANNER +version);
    }
}
