package com.kaikeba.bean;

/**
 * 专门用来前后端交互消息使用的
 */

// 前台收到的：{status:0,result:"",data:{}}
public class Message {
    // 回复客户端的消息内容 0成功 -1失败
    private String result;

    // 消息所携带的一组数据
    private Object data;

    // 状态码(有时候服务器还需要回复一个状态码即可)
    private int status;

    public Message(String result, Object data, int status) {
        this.result = result;
        this.data = data;
        this.status = status;
    }

    public Message() {
    }

    public Message(int status) {
        this.status = status;
    }

    public Message(String result, int status) {
        this.result = result;
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
