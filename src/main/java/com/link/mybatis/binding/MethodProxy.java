package com.link.mybatis.binding;

import com.link.mybatis.exec.Executor;
import com.link.mybatis.mapping.MapperStatement;
import com.link.mybatis.session.Configuration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author lin 2022/12/7 18:38
 * 方法代理实现
 */
public class MethodProxy implements InvocationHandler {
    private final Configuration configuration;
    public MethodProxy(Configuration configuration){
        this.configuration=configuration;
    }
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String statementId=method.getDeclaringClass().getName()+"."+method.getName();
        MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);
        String type=mapperStatement.getType();
        Executor executor=new Executor();
        if(type.equals("insert")){
            executor.execute(configuration,mapperStatement,args);
        }else if(type.equals("select")){
            return executor.query(configuration,mapperStatement);
        }
        return null;
    }
}