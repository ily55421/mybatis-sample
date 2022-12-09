package com.link.mybatis.builder;

import com.link.mybatis.mapping.MapperStatement;
import com.link.mybatis.session.Configuration;
import com.link.mybatis.exception.ParseException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lin 2022/12/7 18:47
 * mapper build对象
 */
public class XMLMapperBuilder {
    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream inputStream) {
        SAXReader reader = SAXReader.createDefault();
        Document doc = null;
        try {
            doc = reader.read(inputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new ParseException("资源解析异常");
        }

        Map<String, MapperStatement> mapperStatementMap = new HashMap<>(16);
        Element root = doc.getRootElement();

        String namespace = root.attributeValue("namespace");

        List<Element> elements = root.elements();
        //解析statement类型 转换储层mapper对象
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
            //关键语句
            mapperStatementMap.put(namespace + "." + id, mapperStatement);
        }

        configuration.setMapperStatementMap(mapperStatementMap);
    }
}
