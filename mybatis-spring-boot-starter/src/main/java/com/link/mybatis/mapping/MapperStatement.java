package com.link.mybatis.mapping;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lin 2022/12/9 20:59
 * 映射声明  字段映射相关信息
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
