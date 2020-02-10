package com.netty;

public class Constants {

    public static final int NETTY_HEADER = 16;
    // netty codec
    public static final short NETTY_MAGIC_TYPE = (short) 0xF1F1;


    public static final String HEARTBEAT_INTERFACE_NAME = "com.weibo.api.motan.rpc.heartbeat";
    public static final String HEARTBEAT_METHOD_NAME = "heartbeat";
    public static final String HHEARTBEAT_PARAM = "void";


    // ------------------ motan 2 protocol constants -----------------
    public static final String M2_GROUP = "M_g";
    public static final String M2_VERSION = "M_v";
    public static final String M2_PATH = "M_p";
    public static final String M2_METHOD = "M_m";
    public static final String M2_METHOD_DESC = "M_md";
    public static final String M2_AUTH = "M_a";
    public static final String M2_SOURCE = "M_s";// 调用方来源标识,等同与application
    public static final String M2_MODULE = "M_mdu";
    public static final String M2_PROXY_PROTOCOL = "M_pp";
    public static final String M2_INFO_SIGN = "M_is";
    public static final String M2_ERROR = "M_e";
    public static final String M2_PROCESS_TIME = "M_pt";
}
