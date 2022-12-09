package com.link.mybatis.session;

import com.link.mybatis.binding.MethodProxy;

import java.lang.reflect.Proxy;

/**
 * @author lin 2022/12/9 21:02
 */
public class SqlSession {
    private final Configuration configuration;

    public SqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    public <T> T getMapper(Class<T> mapperInterface) {
        return (T) Proxy.newProxyInstance(SqlSession.class.getClassLoader(), new Class[]{mapperInterface}, new MethodProxy(configuration));
    }
}
