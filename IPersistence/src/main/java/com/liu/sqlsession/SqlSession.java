package com.liu.sqlsession;

import java.sql.SQLException;
import java.util.List;

/**
 * @author hgvgh
 * @version 1.0
 * @description
 * @date 2020/11/6
 */
public interface SqlSession {

  <E> List<E> selectList(String statementId, Object... paras)
      throws Exception;

  <E> E selectOne(String statementId, Object... param)
      throws Exception;

  <T> boolean insert(String statementId, Object... param) throws NoSuchFieldException, SQLException, ClassNotFoundException, IllegalAccessException;

  <T> int update(String statementId, Object... param) throws ClassNotFoundException, SQLException, IllegalAccessException, NoSuchFieldException;

  <T> boolean delete(String statementId, Object... param) throws ClassNotFoundException, SQLException, IllegalAccessException, NoSuchFieldException;

  void close() throws SQLException;

  <T> T getMapper(Class<?> mapperClass);

}
