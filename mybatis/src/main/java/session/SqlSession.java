package session;

import java.util.List;

/**
 * @Author: huangYong
 * @Date: 2020/5/9 10:14
 */
public interface SqlSession {
    <T> T selectOne(String statement, Object parameter);
    <E> List<E> selectList(String statement, Object parameter);

    <T> T getMapper(Class<T> type);

}
