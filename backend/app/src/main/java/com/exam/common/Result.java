package com.exam.common;


import com.exam.common.enums.EnumCode;
import com.exam.common.enums.impl.ApiEnumCode;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * 返回值实体类
 *
 * @author gaoge
 * @since 2021-11-07 13:42:39
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -3044682540154023066L;
    private boolean flag;
    private long code;
    private T data;
    private String msg;

    public Result() {
    }

    public Result(EnumCode enumCode) {
        enumCode = (EnumCode) Optional.ofNullable(enumCode).orElse(ApiEnumCode.FAILED);
        this.code = enumCode.getCode();
        this.msg = enumCode.getMsg();
    }

    public static <T> Result<T> ok() {
        ApiEnumCode aec = ApiEnumCode.SUCCESS;
        return restResult(true, null, aec);
    }

    public static <T> Result<T> ok(String msg) {

        return restResult(true, null, ApiEnumCode.SUCCESS.getCode(), msg);
    }

    public static <T> Result<T> ok(EnumCode enumCode) {

        return restResult(true, null, enumCode.getCode(), enumCode.getMsg());
    }

    public static <T> Result<T> ok(T data) {
        ApiEnumCode aec = ApiEnumCode.SUCCESS;
        return restResult(true, data, aec);
    }

    public static <T> Result<T> ok(EnumCode enumCode, T data) {
        return restResult(true, data, enumCode);
    }

    public static <T> Result<T> failed() {
        return restResult(false, null, ApiEnumCode.FAILED.getCode(), ApiEnumCode.FAILED.getMsg());
    }

    public static <T> Result<T> failed(String msg) {
        return restResult(false, null, ApiEnumCode.FAILED.getCode(), msg);
    }

    public static <T> Result<T> failed(EnumCode enumCode) {
        return restResult(false, null, enumCode);
    }

    public static <T> Result<T> failed(T data) {
        ApiEnumCode aec = ApiEnumCode.FAILED;
        return restResult(false, data, aec);
    }

    public static <T> Result<T> failed(EnumCode enumCode, T data) {
        return restResult(false, data, enumCode);
    }


    public static <T> Result<T> restResult(boolean flag, T data, EnumCode enumCode) {
        return restResult(flag, data, enumCode.getCode(), enumCode.getMsg());
    }

    private static <T> Result<T> restResult(boolean flag, T data, long code, String msg) {
        Result<T> apiResult = new Result();
        apiResult.setFlag(flag);
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result<?> result = (Result<?>) o;
        return flag == result.flag &&
                code == result.code &&
                data.equals(result.data) &&
                msg.equals(result.msg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flag, code, data, msg);
    }

    @Override
    public String toString() {
        return "Result{" +
                "flag=" + flag +
                ", code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
