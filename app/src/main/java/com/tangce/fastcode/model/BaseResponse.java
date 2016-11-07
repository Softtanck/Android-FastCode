package com.tangce.fastcode.model;

/**
 * Created by Tanck on 11/3/2016.
 * <p>
 * Describe:
 */
public class BaseResponse<T> {
    private String code;
    private String desc;
    private String tag;
    private T msg;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return msg;
    }

    public void setData(T data) {
        this.msg = data;
    }
}
