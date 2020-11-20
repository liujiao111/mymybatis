package com.liu.pojo;

import com.liu.io.Resources;
import com.liu.sqlsession.DefaultSqlSession;
import com.liu.sqlsession.SqlSession;
import com.liu.sqlsession.SqlSessionFactory;
import com.liu.sqlsession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;

public class UserDaoImpl implements IUserDao{


    public List<User> findAll() throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactoryBuilder.openSession();

        User user = new User();
        user.setId(1);
        user.setUsername("lucy");
        List<User> objects = sqlSession.selectList("com.liu.pojo.User.selectList", user);
        return objects;
    }

    public User findByCondition(User user) throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactoryBuilder.openSession();

        User userParam = new User();
        userParam.setId(1);
        userParam.setUsername("lucy");
        User user2 = sqlSession.selectOne("com.liu.pojo.User.selectOne", user);
        return user2;
    }
}
