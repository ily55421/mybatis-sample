package com.link.mybatis.session;

import com.link.mybatis.mapping.MapperStatement;
import lombok.Getter;
import lombok.Setter;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author lin 2022/12/9 20:59
 * 数据源映射以及映射配置mapperStatementMap
 */
@Getter
@Setter
public class Configuration {
    private DataSource dataSource;
    private Map<String, MapperStatement> mapperStatementMap;
}
