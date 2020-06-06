package binding;

import session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @Author: huangYong
 * @Date: 2020/5/9 18:36
 */
public class MapperProxy implements InvocationHandler {
    private SqlSession session;

    public MapperProxy(SqlSession session) {
        this.session = session;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //根据mapper.xml的方法判断SqlSession需要执行的方法
        //判断方法的返回类型是不是collection的子级
        if (Collection.class.isAssignableFrom(method.getReturnType())) {
            return session.selectList(method.getDeclaringClass().getName() + "." + method.getName(), args == null ? null : args[0]);
        } else {
            return session.selectOne(method.getDeclaringClass().getName() + "." + method.getName(), args == null ? null : args[0]);
        }
    }
}
