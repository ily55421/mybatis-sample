package com.link.mybatis.exception;

/**
 * @author lin 2022/12/7 18:49
 * 解析异常
 */
public class ParseException extends RuntimeException {
    public ParseException(String message) {
        super(message);
    }
}
