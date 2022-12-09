package com.link.mybatis.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lin 2022/12/9 21:16
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = DataBase.LINK_MYBATIS)
public class DataBase {
    public static final String LINK_MYBATIS="link.mybatis";

    private String url;
    private String driver;
    private String username;
    private String password;
}
