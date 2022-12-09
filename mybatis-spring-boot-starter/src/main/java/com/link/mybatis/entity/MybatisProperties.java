package com.link.mybatis.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lin 2022/12/9 21:27
 * mybatis的配置类，可以配置别名或者 mapper.xml的位置
 */
@Getter
@Setter
@ConfigurationProperties(prefix = MybatisProperties.LINK_MYBATIS)
public class MybatisProperties {
    public static final String LINK_MYBATIS = "link.mybatis.config";
    private String[] mapperLocations;
}
