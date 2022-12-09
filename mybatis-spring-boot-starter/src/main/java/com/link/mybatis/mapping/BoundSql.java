package com.link.mybatis.mapping;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author lin 2022/12/9 21:10
 * sql包装对象 用于sql 参数的解析
 */
@Getter
@Setter
public class BoundSql {
    private String sql;
    private List<String> parameters;
}
