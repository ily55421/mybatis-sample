package com.link.mybatis.builder;

import com.alibaba.druid.pool.DruidDataSource;
import com.link.mybatis.exception.SqlExecException;
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
 * @author lin 2022/12/9 20:57
 */
public class XMLConfigBuilder {
    private Configuration configuration;

    public XMLConfigBuilder() {
        configuration = new Configuration();
    }

    public Configuration parse(InputStream inputStream) {
        SAXReader reader = new SAXReader();
        Document doc = null;
        try {
            doc = reader.read(inputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new SqlExecException(e.getMessage());
        }
        Element root = doc.getRootElement();
        Element ds = root.element("dataSource");
        List<Element> elements = ds.elements();

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

        configuration.setDataSource(dataSource);  //完成數據源的創建

        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
        Element mappers = root.element("mappers");
        List<Element> ma = mappers.elements("mapper");
        for (Element element : ma) {
            String resource = element.attributeValue("resource");
            xmlMapperBuilder.parse(Resources.getResourceAsStream(resource));
        }
        return configuration;
    }
}
