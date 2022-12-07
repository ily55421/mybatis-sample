package com.link.mybatis.mapping;


import lombok.Getter;
import lombok.Setter;

/**
 * @author lin 2022/12/7 18:39
 * 语句映射对象
 */
@Getter
@Setter
public class MapperStatement {
    private String id;
    private String type;
    private String sql;
    private String parameterType;
    private String resultType;
}
