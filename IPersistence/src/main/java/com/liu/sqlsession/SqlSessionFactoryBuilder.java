package com.liu.sqlsession;

import com.liu.config.XMLConfigBuilder;
import com.liu.pojo.Configuration;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import org.dom4j.DocumentException;

/**
 * @author hgvgh
 * @version 1.0
 * @description
 * @date 2020/11/6
 */
public class SqlSessionFactoryBuilder {

  private Configuration configuration;

  public SqlSessionFactoryBuilder() {
    this.configuration = new Configuration();
  }

  public SqlSessionFactory build(InputStream in)
      throws DocumentException, PropertyVetoException, ClassNotFoundException {

    //解析配置文件，封装Configuration 对象
    XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(configuration);
    Configuration configuration= xmlConfigBuilder.parseCOnfiguration(in);


    //创建SqlSessionFactory
    SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
    return sqlSessionFactory;
  }

}
