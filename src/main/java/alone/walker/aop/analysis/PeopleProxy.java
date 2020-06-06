package alone.walker.aop.analysis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: huangYong
 * @Date: 2020/3/12 17:58
 * 动态代理实现
 */
public class PeopleProxy implements InvocationHandler {

    private Girl girl;

    public PeopleProxy(Girl girl) {
        this.girl = girl;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object invoke = method.invoke(girl, args);
        end();
        return invoke;
    }

    /***
     * 可以在代理对象方法执行前加入逻辑
     */
    private void before(){

    }
    /***
     * 可以在代理对象方法执行后加入逻辑
     */
    private void end(){

    }

    /***
     * 获取动态代理实现类
     * @return
     */
    public  Object getProxyInstance(){
        return Proxy.newProxyInstance(girl.getClass().getClassLoader(),girl.getClass().getInterfaces(), this);
    }
}
