package com.link.mybatis.binding;

import com.link.mybatis.exec.Executor;
import com.link.mybatis.mapping.MapperStatement;
import com.link.mybatis.session.Configuration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author lin 2022/12/9 20:57
 */
public class MethodProxy implements InvocationHandler {
    private Configuration configuration;

    public MethodProxy(Configuration configuration) {
        this.configuration = configuration;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String id = method.getDeclaringClass().getName() + "." + method.getName();
        MapperStatement mapperStatement = configuration.getMapperStatementMap().get(id);
        // 执行类型信息
        String type = mapperStatement.getType();
        Executor executor = new Executor();
        switch (type) {
            case "insert":
                executor.execute(configuration, mapperStatement, args);
                return null;
            case "select":
                return executor.query(configuration, mapperStatement);
            default:
                return null;
        }

    }
}
