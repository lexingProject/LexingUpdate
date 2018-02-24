package com.lexing360.app.lexingupdate.model;

/**
 * Created by fenglingfeng on 2018/2/5.
 */

public class ResponseModel {


    /**
     * code : 500
     * message : No message available
     * exception : java.lang.NullPointerException
     */

    private int code;
    private String message;
    private String exception;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
