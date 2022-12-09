package com.link.mybatis.exec;

import com.link.mybatis.mapping.BoundSql;
import com.link.mybatis.mapping.MapperStatement;
import com.link.mybatis.session.Configuration;
import org.apache.commons.text.StringSubstitutor;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author lin 2022/12/9 21:11
 */
public class Executor {
    public Connection getConnection(Configuration configuration) {
        try {
            return configuration.getDataSource().getConnection();
        } catch (SQLException ignore) {
            //sql解析异常
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
        PreparedStatement ps = null;
        try {
            ps = getConnection(configuration).prepareStatement(boundSql.getSql());
            List<String> parameters = boundSql.getParameters();
            //循环为？赋值
            for (int i = 0; i < parameters.size(); i++) {
                PropertyDescriptor pd = new PropertyDescriptor(parameters.get(i), c);
                Method getMethod = pd.getReadMethod();
                ps.setObject(i + 1, getMethod.invoke(args[0]));
            }
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 执行查询语句
     *
     * @param configuration   sql配置信息
     * @param mapperStatement 映射规则
     * @param <T>             泛型信息
     * @return list
     */
    public <T> List<T> query(Configuration configuration, MapperStatement mapperStatement) {
        String sql = mapperStatement.getSql();
        PreparedStatement ps = null;
        try {
            ps = getConnection(configuration).prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            //处理结果集类型转换
            return handlerResultSet(resultSet, (Class<T>) Class.forName(mapperStatement.getResultType()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * 解析带有#{}占位符的SQL
     *
     * @param sql sql语句
     * @return sql包装对象
     */
    private BoundSql getBoundSql(String sql) {
        List<String> parameters = new ArrayList<>();
        StringSubstitutor stringSubstitutor = new StringSubstitutor();
        stringSubstitutor.setVariablePrefix("#{");
        stringSubstitutor.setVariableSuffix("}");
        // 设置变量解析器 用于替换   将变量参数 替换为 ? 用于解析赋值
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
     * @param c   类信息
     * @param <T> 泛型信息
     * @return list
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
