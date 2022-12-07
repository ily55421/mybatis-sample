package com.link.mybatis.session;

import com.link.mybatis.builder.XMLConfigBuilder;

import java.io.InputStream;

/**
 * @author lin 2022/12/7 18:46
 */
public class SqlSessionFactoryBuilder {
    private SqlSessionFactoryBuilder
            () {
    }

    public static SqlSessionFactory build(InputStream inputStream) {
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parse(inputStream);
        return new SqlSessionFactory(configuration);
    }
}
