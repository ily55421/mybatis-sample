package com.link.mybatis.builder;

import com.alibaba.druid.pool.DruidDataSource;
import com.link.mybatis.exception.ParseException;
import com.link.mybatis.io.Resources;
import com.link.mybatis.session.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author lin 2022/12/7 18:46
 * Configuration build对象
 */
public class XMLConfigBuilder {
    private Configuration configuration;

    public XMLConfigBuilder() {
        configuration = new Configuration();
    }

    public Configuration parse(InputStream inputStream) {
        SAXReader reader = SAXReader.createDefault();
        Document doc = null;
        try {
            doc = reader.read(inputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new ParseException("数据解析异常");
        }
        Element root = doc.getRootElement();
        Element property = root.element("dataSource");
        List<Element> elements = property.elements();

        // 解析配置信息
        Properties p = new Properties();
        for (Element element : elements) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            p.setProperty(name, value);
        }

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(p.getProperty("driver"));
        dataSource.setUrl(p.getProperty("url"));
        dataSource.setUsername(p.getProperty("username"));
        dataSource.setPassword(p.getProperty("password"));
        //完成數據源的創建
        configuration.setDataSource(dataSource);

        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
        //获取mappers信息
        Element mappers = root.element("mappers");
        if (mappers != null) {
            List<Element> ma = mappers.elements("mapper");
            for (Element element : ma) {
                String resource = element.attributeValue("resource");
                xmlMapperBuilder.parse(Resources.getResourceAsStream(resource));
            }
        }

        return configuration;
    }
}
