package com.link.mybatis.entity;

import com.link.mybatis.session.Configuration;
import com.link.mybatis.session.SqlSessionFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author lin 2022/12/9 21:28
 * sql会话工厂bean
 */
public class SqlSessionFactoryBean implements FactoryBean<SqlSessionFactory> {
    Configuration configuration;

    public SqlSessionFactoryBean(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSessionFactory getObject() throws Exception {
        return new SqlSessionFactory(configuration);
    }

    @Override
    public Class<?> getObjectType() {
        return SqlSessionFactory.class;
    }
}
