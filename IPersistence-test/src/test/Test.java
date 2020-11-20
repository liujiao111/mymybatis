import com.liu.io.Resources;
import com.liu.pojo.User;
import com.liu.sqlsession.SqlSession;
import com.liu.sqlsession.SqlSessionFactory;
import com.liu.sqlsession.SqlSessionFactoryBuilder;
import com.liu.mapper.UserMapper;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;

/**
 * @author hgvgh
 * @version 1.0
 * @description
 * @date 2020/11/13
 */
public class Test {


  @org.junit.Test
  public void testSelectList() throws Exception{
      final InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
      final SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
      final SqlSession sqlSession = build.openSession();
      UserMapper mapper = sqlSession.getMapper(UserMapper.class);
      User user = new User();

      final List<User> list = mapper.selectList(user);
      for (User user1 : list) {
          System.out.println(user1);
      }
  }

  @org.junit.Test
  public void testSelectOne() throws Exception {
      final InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
      final SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
      final SqlSession sqlSession = build.openSession();
      UserMapper mapper = sqlSession.getMapper(UserMapper.class);
      User user = new User();
      user.setId(2);
      user.setUsername("tom");
      final User byCondition = mapper.selectOne(user);
      System.out.println(byCondition);
  }

    /**
     * 测试新增
     * @throws DocumentException
     * @throws PropertyVetoException
     * @throws ClassNotFoundException
     */
  @org.junit.Test
    public void testInsert() throws DocumentException, PropertyVetoException, ClassNotFoundException {
      final InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
      final SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
      final SqlSession sqlSession = build.openSession();
      UserMapper mapper = sqlSession.getMapper(UserMapper.class);
      User user = new User();
      user.setId(8);
      user.setUsername("liujiao");
      user.setPassword("dfdfd");
      user.setBirthday("2020-11-13 02:02:01");
      final boolean insert = mapper.insert(user);
      System.out.println(insert);
  }

    @org.junit.Test
    public void testUpdate() throws DocumentException, PropertyVetoException, ClassNotFoundException {
        final InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        final SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        final SqlSession sqlSession = build.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(8);
        user.setUsername("戴鑫是刘皎的小宝贝");
        user.setPassword("dfdfd");
        user.setBirthday("2020-11-13 02:02:01");
        final int insert = mapper.update(user);
        System.out.println(insert);
    }

    @org.junit.Test
    public void testDelete() throws DocumentException, PropertyVetoException, ClassNotFoundException {
        final InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        final SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        final SqlSession sqlSession = build.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        final boolean insert = mapper.delete(8);
        System.out.println(insert);
    }

}
