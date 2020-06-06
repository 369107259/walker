package session;

import binding.MapperProxy;
import config.Configuration;
import config.MapperStatement;
import executor.DefaultExecutor;
import executor.Executor;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @Author: huangYong
 * @Date: 2020/5/9 11:47
 * SqlSession
 * 对外提供数据访问api 对内将请求转发给executor
 */
public class DefaultSqlSession implements SqlSession {
    private final Configuration configuration;
    private Executor executor;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        executor = new DefaultExecutor(configuration);
    }

    public <T> T selectOne(String statement, Object parameter) {
        List<Object> objects = this.selectList(statement, parameter);
        if (null==objects || objects.size()==0){
            return null;
        }
        if (objects.size()==1){
            return (T) objects.get(0);
        }else {
            throw new RuntimeException("To Many Results");
        }
    }

    public <E> List<E> selectList(String statement, Object parameter) {
        MapperStatement mapperStatement = configuration.getMapperStatements().get(statement);
        return executor.query(mapperStatement,parameter);
    }

    public <T> T getMapper(Class<T> type) {
        MapperProxy mapperProxy = new MapperProxy(this);
        return (T) Proxy.newProxyInstance(type.getClassLoader(),new Class[]{type}, mapperProxy);
    }
}
