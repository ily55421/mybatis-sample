package com.link.mybatis.mapping;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author lin 2022/12/7 18:55
 * BoundSql 限界失sql拼接
 */
@Setter
@Getter
public class BoundSql {
    private String sql;
    private List<String> parameters;
}
