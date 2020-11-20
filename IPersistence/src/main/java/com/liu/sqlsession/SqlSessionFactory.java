package com.liu.sqlsession;

/**
 * @author hgvgh
 * @version 1.0
 * @description
 * @date 2020/11/6
 */
public interface SqlSessionFactory {

  public SqlSession openSession();

}
