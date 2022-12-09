package com.link.mybatis.autoconfiguration;

import com.alibaba.druid.pool.DruidDataSource;
import com.link.mybatis.builder.XMLMapperBuilder;
import com.link.mybatis.entity.DataBase;
import com.link.mybatis.entity.MybatisProperties;
import com.link.mybatis.entity.SqlSessionFactoryBean;
import com.link.mybatis.io.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author lin 2022/12/9 20:56
 * 自动配置类
 * EnableConfigurationProperties 启用配置类
 * ConditionalOnClass 针对类条件启用
 */
@Configuration
@ConditionalOnClass(DataBase.class)
@EnableConfigurationProperties({DataBase.class, MybatisProperties.class})
public class MybatisAutoConfiguration {
    private DataBase dataBase;
    /**
     * 读取mapper.xml所在的位置
     */
    @Autowired
    MybatisProperties mybatisProperties;

    public MybatisAutoConfiguration(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Bean
    public DruidDataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(dataBase.getUrl());
        druidDataSource.setDriverClassName(dataBase.getDriver());
        druidDataSource.setUsername(dataBase.getUsername());
        druidDataSource.setPassword(dataBase.getPassword());
        return druidDataSource;
    }

    @Bean
    public com.link.mybatis.session.Configuration configuration(DruidDataSource dataSource) {
        // 区分注解名 这里全限定类名做区分
        com.link.mybatis.session.Configuration configuration = new com.link.mybatis.session.Configuration();
        configuration.setDataSource(dataSource);
        String[] mapperLocations = mybatisProperties.getMapperLocations();
        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
        //存入mapper.xml文件
        for (String mapperLocation : mapperLocations) {
            //通过我们配置的xml位置，传入文件中进行解析
            xmlMapperBuilder.parse(Resources.getResourceAsStream(mapperLocation));
        }
        return configuration;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(com.link.mybatis.session.Configuration configuration) {
        return new SqlSessionFactoryBean(configuration);
    }
}
