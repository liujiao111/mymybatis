package com.liu.config;

import com.liu.pojo.Configuration;
import com.liu.pojo.MappedStatement;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author hgvgh
 * @version 1.0
 * @description 解析mapper文件
 * @date 2020/11/6
 */
public class XMLMapperBuilder {

  private Configuration configuration;

  public XMLMapperBuilder(Configuration configuration) {
    this.configuration = configuration;
  }

  public void parse(InputStream resourceAsStream) throws DocumentException, ClassNotFoundException {
    Document document = new SAXReader().read(resourceAsStream);
    Element rootElement = document.getRootElement();
    String namespace = rootElement.attributeValue("namespace");
    List<Element> elementList = new ArrayList<>();
    List<Element> selectElements = rootElement.selectNodes("//select");
    List<Element> insertElements = rootElement.selectNodes("//insert");
    List<Element> updateElements = rootElement.selectNodes("//update");
    List<Element> deleteElements = rootElement.selectNodes("//delete");
    elementList.addAll(selectElements);
    elementList.addAll(insertElements);
    elementList.addAll(updateElements);
    elementList.addAll(deleteElements);

    for (Element selectElement : elementList) {
      String id = selectElement.attributeValue("id");
      String paramterType = selectElement.attributeValue("paramterType");
      String resultType = selectElement.attributeValue("resultType");

      Class<?> resultTypeClass = getClassType(resultType);
      Class<?> paramterTypeClass = getClassType(paramterType);
      String sql = selectElement.getTextTrim();

      String key = namespace + "." + id; //

      //填充Configuration
      MappedStatement mappedStatement = new MappedStatement();
      mappedStatement.setId(id);
      mappedStatement.setParamterType(paramterType);
      mappedStatement.setResultType(resultType);
      mappedStatement.setSql(sql);
      Map<String, MappedStatement> mappedStatementMap = configuration.getMappedStatementMap();
      mappedStatementMap.put(key, mappedStatement);
    }
  }

  private Class<?> getClassType(String className) throws ClassNotFoundException {
    return Class.forName(className);
  }
}
