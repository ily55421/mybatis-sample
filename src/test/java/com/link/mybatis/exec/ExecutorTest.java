package com.link.mybatis.exec;

import com.link.mybatis.io.Resources;
import com.link.mybatis.session.SqlSession;
import com.link.mybatis.session.SqlSessionFactory;
import com.link.mybatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;

/**
 * @author lin 2022/12/7 19:07
 */
public class ExecutorTest {

    @Test
    public void getConnection() throws IOException {
        SqlSessionFactory build = SqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        Executor executor = new Executor();
        System.out.println(build.getConfiguration().getDataSource());
        Connection connection = executor.getConnection(build.getConfiguration());
        System.out.println(connection);

    }


    @Test
    public void testInsert() throws Exception {
        User user = new User();
        user.setAccount("James");
        user.setPassword("123");

        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        //产生的代理
        UserMapper userMapper = session.getMapper(UserMapper.class);
        userMapper.insert(user);
    }

    @Test
    public void testQuery() throws Exception {

        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        //产生的代理
        UserMapper userMapper = session.getMapper(UserMapper.class);
        userMapper.getAll().forEach(System.out::println);
    }
}