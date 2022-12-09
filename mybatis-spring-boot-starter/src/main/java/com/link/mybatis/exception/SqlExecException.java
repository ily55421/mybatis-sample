package com.link.mybatis.exception;

/**
 * @author lin 2022/12/9 21:25
 * sql执行异常信息
 */
public class SqlExecException extends RuntimeException {
    public SqlExecException(String message) {
        super(message);
    }
}
