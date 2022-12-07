package com.link.mybatis.exec;

import com.link.mybatis.io.Resources;
import com.link.mybatis.session.SqlSessionFactory;
import com.link.mybatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;

/**
 * @author lin 2022/12/7 19:07
 */
public class ExecutorTest {

    @Test
    public void getConnection() throws IOException {
        SqlSessionFactory build = SqlSessionFactoryBuilder.build(Resources.getResourceAsStream("dataSource.xml"));
        Executor executor = new Executor();
        System.out.println(build.getConfiguration().getDataSource());
        Connection connection = executor.getConnection(build.getConfiguration());
        System.out.println(connection);

    }
}