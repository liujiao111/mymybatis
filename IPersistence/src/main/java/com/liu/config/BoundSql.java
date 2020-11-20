package com.liu.config;

import com.liu.utils.ParameterMapping;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hgvgh
 * @version 1.0
 * @description
 * @date 2020/11/6
 */
public class BoundSql {

  //解析后的sql语句
  private String sqlText;

  //解析出来的参数
  private List<ParameterMapping> parameterMappingList  = new ArrayList<ParameterMapping>();

  public BoundSql(String sqlText, List<ParameterMapping> parameterMappings) {
    this.sqlText = sqlText;
    this.parameterMappingList = parameterMappings;
  }

  public String getSqlText() {
    return sqlText;
  }

  public void setSqlText(String sqlText) {
    this.sqlText = sqlText;
  }

  public List<ParameterMapping> getParameterMappingList() {
    return parameterMappingList;
  }

  public void setParameterMappingList(List<ParameterMapping> parameterMappingList) {
    this.parameterMappingList = parameterMappingList;
  }
}
