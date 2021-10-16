package com.kaikeba.util;

import com.google.gson.Gson;

/**
 * 用于json格式化的工具,用于服务于把对象转换为json格式去发送
 * 台和后台需要大量的交互，交互的格式进行统一会很方便
 * 前台需要什么样的接口后台去提供，用什么样子的数据格式
 */
public class JSONUtil {
    private static Gson g;
    private static String toJson(Object o){
        return g.toJson(g);
    }
}
