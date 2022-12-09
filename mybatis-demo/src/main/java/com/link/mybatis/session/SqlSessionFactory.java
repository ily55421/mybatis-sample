package com.link.mybatis.session;

/**
 * @author lin 2022/12/7 18:45
 * session工厂类
 */
public class SqlSessionFactory {
    private final Configuration configuration;

    public SqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlSession openSession() {
        return new SqlSession(configuration);
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
