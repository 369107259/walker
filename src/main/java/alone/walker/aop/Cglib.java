package alone.walker.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: huangYong
 * @Date: 2020/3/12 17:51
 * 动态代理构造工厂
 * InvocationHandler
 * proxy
 */
public class Cglib  implements InvocationHandler {

    /**
     * 需要代理得类
     */
    private Object factory;

    /***
     * 类构造方法
     * @param factory
     */
    public Cglib(Object factory) {
        this.factory = factory;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //实现执行前方法增强
        before();
        Object invoke = method.invoke(factory, args);
        //实现执行后方法增强
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

    public Object getInstance(){
        return Proxy.newProxyInstance(factory.getClass().getClassLoader(),factory.getClass().getInterfaces(),this);
    }
}
