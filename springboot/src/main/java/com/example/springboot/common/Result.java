package com.example.springboot.common;

/**
 *统一返回的包装类
 */
public class Result {
    private String code;
    private String msg;
    private Object data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Result(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success() {
        return new Result("200", "success", null);
    }

    public static Result success(Object data) {
        return new Result("200", "请求成功", data);
    }

    public static Result error() {
        return new Result("500", "系统错误", null);
    }

    public static Result error(String code, String msg) {
        Result result = new Result(code, msg, null);
        return result;
    }

    public static Result error(String msg) {
        return new Result("500", msg, null);
    }
}
