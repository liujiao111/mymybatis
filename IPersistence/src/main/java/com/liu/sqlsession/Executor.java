package com.liu.sqlsession;

import com.liu.pojo.Configuration;
import com.liu.pojo.MappedStatement;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author hgvgh
 * @version 1.0
 * @description
 * @date 2020/11/6
 */
public interface Executor {

  <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object[] param)
      throws SQLException, NoSuchFieldException, IllegalAccessException, InstantiationException, IntrospectionException, InvocationTargetException, ClassNotFoundException, Exception;

  void close() throws SQLException;


  boolean insert(Configuration configuration, MappedStatement mappedStatement, Object[] params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException;

  int update(Configuration configuration, MappedStatement mappedStatement, Object[] params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException;

  boolean delete(Configuration configuration, MappedStatement mappedStatement, Object[] params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException;

}
