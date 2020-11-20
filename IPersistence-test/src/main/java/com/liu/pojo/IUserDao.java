package com.liu.pojo;

import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.util.List;

public interface IUserDao {
    public List<User> findAll() throws Exception;
    public User findByCondition(User user) throws Exception;

}
