package com.liu.sqlsession;

import com.liu.config.BoundSql;
import com.liu.pojo.Configuration;
import com.liu.pojo.MappedStatement;
import com.liu.utils.GenericTokenParser;
import com.liu.utils.ParameterMapping;
import com.liu.utils.ParameterMappingTokenHandler;
import com.mysql.jdbc.StringUtils;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hgvgh
 * @version 1.0
 * @description
 * @date 2020/11/6
 */


public class SimpleExeCutor implements  Executor {


    @Override                                                                                //user
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
      PreparedStatement preparedStatement = getPreparedStatement(configuration, mappedStatement, params);


      // 5. 执行sql
      ResultSet resultSet = preparedStatement.executeQuery();
      String resultType = mappedStatement.getResultType();
      Class<?> resultTypeClass = getClassType(resultType);

      ArrayList<Object> objects = new ArrayList<>();

      // 6. 封装返回结果集
      while (resultSet.next()){
        Object o =resultTypeClass.newInstance();
        //元数据
        ResultSetMetaData metaData = resultSet.getMetaData();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {

          // 字段名
          String columnName = metaData.getColumnName(i);
          // 字段的值
          Object value = resultSet.getObject(columnName);

          //使用反射或者内省，根据数据库表和实体的对应关系，完成封装
          PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
          Method writeMethod = propertyDescriptor.getWriteMethod();
          writeMethod.invoke(o,value);


        }
        objects.add(o);

      }
      return (List<E>) objects;

    }

  @Override
  public void close() throws SQLException {

  }

  @Override
  public int update(Configuration configuration, MappedStatement mappedStatement, Object[] params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {

    PreparedStatement preparedStatement = getPreparedStatement(configuration, mappedStatement, params);

    // 5. 执行sql
    return preparedStatement.executeUpdate();
  }

  @Override
  public boolean delete(Configuration configuration, MappedStatement mappedStatement, Object[] params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {

    // 1. 注册驱动，获取连接
    Connection connection = configuration.getDataSource().getConnection();


    String sql = mappedStatement.getSql();
    BoundSql boundSql = getBoundSql(sql);

    // 3.获取预处理对象：preparedStatement
    PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

    // 4. 设置参数
    //获取到了参数的全路径
    String paramterType = mappedStatement.getParamterType();
    Class<?> paramtertypeClass = getClassType(paramterType);

    List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
    preparedStatement.setObject(1, params[0]);

    // 5. 执行sql
    return preparedStatement.execute();
  }

  @Override
  public boolean insert(Configuration configuration, MappedStatement mappedStatement, Object[] params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {

    PreparedStatement preparedStatement = getPreparedStatement(configuration, mappedStatement, params);

    // 5. 执行sql
    return preparedStatement.execute();
  }

  private PreparedStatement getPreparedStatement(Configuration configuration, MappedStatement mappedStatement, Object[] params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {

    // 1. 注册驱动，获取连接
    Connection connection = configuration.getDataSource().getConnection();


    String sql = mappedStatement.getSql();
    BoundSql boundSql = getBoundSql(sql);

    // 3.获取预处理对象：preparedStatement
    PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

    // 4. 设置参数
    //获取到了参数的全路径
    String paramterType = mappedStatement.getParamterType();
    Class<?> paramtertypeClass = getClassType(paramterType);

    List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
    for (int i = 0; i < parameterMappingList.size(); i++) {
      ParameterMapping parameterMapping = parameterMappingList.get(i);
      String content = parameterMapping.getContent();

      //反射
      Field declaredField = paramtertypeClass.getDeclaredField(content);
      //暴力访问
      declaredField.setAccessible(true);
      Object o = declaredField.get(params[0]);

      preparedStatement.setObject(i+1,o);

    }
    return preparedStatement;
  }


  private Class<?> getClassType(String paramterType) throws ClassNotFoundException {
      if(paramterType!=null){
        Class<?> aClass = Class.forName(paramterType);
        return aClass;
      }
      return null;

    }


    /**
     * 完成对#{}的解析工作：1.将#{}使用？进行代替，2.解析出#{}里面的值进行存储
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {
      //标记处理类：配置标记解析器来完成对占位符的解析处理工作
      ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
      GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
      //解析出来的sql
      String parseSql = genericTokenParser.parse(sql);
      //#{}里面解析出来的参数名称
      List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();

      BoundSql boundSql = new BoundSql(parseSql,parameterMappings);
      return boundSql;

    }


  }

