package top.ruix;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import top.ruix.dao.IUserDao;
import top.ruix.entity.UserEntity;

import java.io.IOException;


public class TestUserDao {

    SqlSession sqlSession;

    @Before
    public void init() {
        try {
            sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config.xml")).openSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUserFindById() {
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        UserEntity userEntity = userDao.findById(1);
        System.out.println(userEntity);
    }

}
