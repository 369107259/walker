import entity.User;
import mapper.UserMapper;
import session.SqlSession;
import session.SqlSessionFactory;

/**
 * @Author: huangYong
 * @Date: 2020/5/9 10:24
 */
public class TestMybatis {
    public static void main(String[] args) {
        //实例化SqlSessionFactory,加载数据库配置文件以及mapper.xml到Configuration对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactory();
        //获取sqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        System.out.println(sqlSession);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectByPrimary((long) 1);
        System.out.println(user);
    }
}
