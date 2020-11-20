package com.liu.sqlsession;

import com.liu.pojo.Configuration;

/**
 * @author hgvgh
 * @version 1.0
 * @description
 * @date 2020/11/6
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

  private Configuration configuration;

  public DefaultSqlSessionFactory(Configuration configuration) {
    this.configuration = configuration;
  }

  @Override
  public SqlSession openSession() {
    return new DefaultSqlSession(configuration);
  }
}
