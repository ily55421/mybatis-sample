package com.link.mybatis.exec;

import com.link.mybatis.mapping.BoundSql;
import com.link.mybatis.mapping.MapperStatement;
import com.link.mybatis.session.Configuration;
import org.apache.commons.text.StringSubstitutor;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author lin 2022/12/7 18:56
 * sql 执行对象
 */
public class Executor {
    public Connection getConnection(Configuration configuration) {
        try {
            return configuration.getDataSource().getConnection();
        } catch (SQLException ignore) {
            //
        }
        return null;
    }

    public void execute(Configuration configuration, MapperStatement mapperStatement, Object... args) {
        String parameterType = mapperStatement.getParameterType();
        Class<?> c = null;
        try {
            c = Class.forName(parameterType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sql = mapperStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection(configuration).prepareStatement(boundSql.getSql());
            List<String> parameters = boundSql.getParameters();
            //循环为？赋值
            for (int i = 0; i < parameters.size() && c != null; i++) {
                PropertyDescriptor pd = new PropertyDescriptor(parameters.get(i), c);
                Method getMethod = pd.getReadMethod();
                preparedStatement.setObject(i + 1, getMethod.invoke(args[0]));
            }
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public <T> List<T> query(Configuration configuration, MapperStatement mapperStatement) {
        String sql = mapperStatement.getSql();
        PreparedStatement ps = null;
        try {
            ps = getConnection(configuration).prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            return handlerResultSet(resultSet, (Class<T>) Class.forName(mapperStatement.getResultType()));
        } catch (Exception ignore) {
            //
        }
        return Collections.emptyList();
    }

    /**
     * 解析带有#{}占位符的SQL
     *
     * @param sql sql语句
     * @return 包装sql对象
     */
    private BoundSql getBoundSql(String sql) {
        List<String> parameters = new ArrayList<>();
        StringSubstitutor stringSubstitutor = new StringSubstitutor();
        stringSubstitutor.setVariablePrefix("#{");
        stringSubstitutor.setVariableSuffix("}");
        stringSubstitutor.setVariableResolver(k -> {
            parameters.add(k);
            return "?";
        });
        BoundSql boundSql = new BoundSql();
        boundSql.setSql(stringSubstitutor.replace(sql));
        boundSql.setParameters(parameters);
        return boundSql;


    }

    /**
     * 解析SQL，封装查询的结果集
     *
     * @param rs  结果集
     * @param c   类映射
     * @param <T> 泛型
     * @return 数据
     */
    private <T> List<T> handlerResultSet(ResultSet rs, Class<T> c) {
        List<T> result = new ArrayList<>();

        try {
            while (rs.next()) {
                T t = c.newInstance();
                ResultSetMetaData metaData = rs.getMetaData();
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    String columnName = metaData.getColumnName(i + 1);
                    PropertyDescriptor pd = new PropertyDescriptor(columnName, c);
                    Method writeMethod = pd.getWriteMethod();
                    writeMethod.invoke(t, rs.getObject(columnName));
                }
                result.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
