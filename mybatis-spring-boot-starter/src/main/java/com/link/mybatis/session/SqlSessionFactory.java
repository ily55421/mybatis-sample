package com.link.mybatis.session;

/**
 * @author lin 2022/12/9 21:07
 * sql 会话简易工厂 默认获取一个新的链接
 */
public class SqlSessionFactory {
    private final Configuration configuration;

    public SqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlSession openSession() {
        return new SqlSession(configuration);
    }
}
