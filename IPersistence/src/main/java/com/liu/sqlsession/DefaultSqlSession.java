package com.liu.sqlsession;

import com.liu.pojo.Configuration;
import com.liu.pojo.MappedStatement;

import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hgvgh
 * @version 1.0
 * @description
 * @date 2020/11/6
 */
public class DefaultSqlSession implements SqlSession {

  private Configuration configuration;

  private Executor simpleExecutor = new SimpleExeCutor();


  public DefaultSqlSession(Configuration configuration) {
    this.configuration = configuration;
  }

  @Override
  public <E> List<E> selectList(String statementId, Object... param)
      throws Exception {
    MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
    List<E> result = simpleExecutor.query(configuration, mappedStatement, param);
    return result;
  }

  @Override
  public <E> E selectOne(String statementId, Object... param)
      throws Exception {
    List<Object> objects = selectList(statementId, param);
    if(objects.size() == 1) {
      return (E) objects.get(0);
    }
      throw new RuntimeException("返回结果不正确");
  }

  @Override
  public  boolean insert(String statementId, Object... param) throws NoSuchFieldException, SQLException, ClassNotFoundException, IllegalAccessException {
    MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
    return simpleExecutor.insert(configuration, mappedStatement, param);
  }


  @Override
  public int update(String statementId, Object... param) throws ClassNotFoundException, SQLException, IllegalAccessException, NoSuchFieldException {
    MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
    return simpleExecutor.update(configuration, mappedStatement, param);
  }

  @Override
  public boolean delete(String statementId, Object... param) throws ClassNotFoundException, SQLException, IllegalAccessException, NoSuchFieldException {
    MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
    return simpleExecutor.delete(configuration, mappedStatement, param);
  }

  @Override
  public void close() throws SQLException {
    simpleExecutor.close();
  }

  //使用JDK动态代理来为DAO借口生成代理对象，并返回
  @Override
  public <T> T getMapper(Class<?> mapperClass) {
    final Object o = Proxy.newProxyInstance(mapperClass.getClassLoader(), new Class[
            ]{mapperClass}, new InvocationHandler() {


      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String methodName = method.getName();
        String className =
                method.getDeclaringClass().getName();
        String key = className + "." + methodName;
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(key);
        final Type genericReturnType = method.getGenericReturnType();

        ArrayList<Object> arrayList = new ArrayList<>();

        //判断是否实现泛型参数化
        if (genericReturnType instanceof ParameterizedType) {
          return selectList(key, args);
        } else if(methodName.trim().startsWith("insert")) {
          return insert(key, args);
        } else if(methodName.trim().startsWith("delete")) {
          return delete(key, args);
        } else if(methodName.trim().startsWith("update")){
          return update(key, args);
        }else {
          return selectOne(key, args);
        }
      }
    });
    return (T) o;
  }
}
