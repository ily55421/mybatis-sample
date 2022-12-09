package com.link.mybatis.session;

import com.link.mybatis.mapping.MapperStatement;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author lin 2022/12/7 18:39
 * 配置类
 */
public class Configuration {
    /**
     * 数据源信息
     */
    private DataSource dataSource;
    /**
     * 映射对象
     */
    private Map<String, MapperStatement> mapperStatementMap;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MapperStatement> getMapperStatementMap() {
        return mapperStatementMap;
    }

    public void setMapperStatementMap(Map<String, MapperStatement> mapperStatementMap) {
        this.mapperStatementMap = mapperStatementMap;
    }
}
