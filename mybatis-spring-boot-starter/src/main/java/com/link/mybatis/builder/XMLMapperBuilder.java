package com.link.mybatis.builder;

import com.link.mybatis.mapping.MapperStatement;
import com.link.mybatis.session.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lin 2022/12/9 21:18
 */
public class XMLMapperBuilder {
    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream inputStream) {
        SAXReader reader = new SAXReader();
        Document doc = null;
        try {
            doc = reader.read(inputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        Map<String, MapperStatement> mapperStatementMap = new HashMap<>(16);
        Element root = doc.getRootElement();

        String namespace = root.attributeValue("namespace");

        List<Element> elements = root.elements();
        // 解析xml种的配置信息 并存储在映射规则map种
        for (Element element : elements) {
            String id = element.attributeValue("id");
            String type = element.getName();
            String parameterType = element.attributeValue("parameterType");
            String resultType = element.attributeValue("resultType");
            String sql = element.getTextTrim();

            MapperStatement mapperStatement = new MapperStatement();
            mapperStatement.setId(id);
            mapperStatement.setType(type);
            mapperStatement.setParameterType(parameterType);
            mapperStatement.setResultType(resultType);
            mapperStatement.setSql(sql);
            //关键语句  存储sql的位置和参数信息 key必须唯一
            mapperStatementMap.put(namespace + "." + id, mapperStatement);
        }
        //封装进配置信息中
        configuration.setMapperStatementMap(mapperStatementMap);
    }
}
