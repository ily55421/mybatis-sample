package com.link.mybatis.session;

import com.link.mybatis.builder.XMLConfigBuilder;

import java.io.InputStream;

/**
 * @author lin 2022/12/9 21:08
 * sql会话工厂 建造类 静态方法获取工厂对象
 */
public class SqlSessionFactoryBuilder {
    public static SqlSessionFactory build(InputStream inputStream) {
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parse(inputStream);
        return new SqlSessionFactory(configuration);
    }
}
