package com.liu.config;

import com.liu.io.Resources;
import com.liu.pojo.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import javax.xml.transform.sax.SAXResult;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author hgvgh
 * @version 1.0
 * @description
 * @date 2020/11/6
 */
public class XMLConfigBuilder {

  private Configuration configuration;

  public XMLConfigBuilder(Configuration configuration) {
    this.configuration = new Configuration();
  }

  public Configuration parseCOnfiguration(InputStream in)
      throws DocumentException, PropertyVetoException, ClassNotFoundException {

    //读取数据库配置文件
    Document document = new SAXReader().read(in);
    Element rootElement = document.getRootElement();
    List<Element> propertiesElements = rootElement.selectNodes("//property");

    Properties properties = new Properties();
    for (Element propertiesElement : propertiesElements) {
      String name = propertiesElement.attributeValue("name");
      String value = propertiesElement.attributeValue("value");
      properties.setProperty(name, value);
    }

    //连接池
    ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
    comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
    comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
    comboPooledDataSource.setUser(properties.getProperty("user"));
    comboPooledDataSource.setPassword(properties.getProperty("password"));

    configuration.setDataSource(comboPooledDataSource);

    //mapper解析
    XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
    List<Element> mapperElements = rootElement.selectNodes("//mapper");
    for (Element mapperElement : mapperElements) {
      String mapperPath = mapperElement.attributeValue("resource");
      InputStream resourceAsStream = Resources.getResourceAsStream(mapperPath);
      xmlMapperBuilder.parse(resourceAsStream);
    }
    return configuration;
  }
}
