package alone.walker.annotation.interceptor;

import alone.walker.annotation.AccessCount;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: huangYong
 * @Date: 2019/11/13 16:47
 * 统计带有自定义注解的接口访问次数
 */
public class AccessCountInterceptor implements HandlerInterceptor {
    /***
     * 类使用的是cas原理（通过旧值与内存中的值比较,相等则操作,不等则重新取值再做比较）
     * 缺陷：只能保证单一的原子性 , 会产生ABA问题 ,循环时间过长开销很大
     */
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class<?> aClass = handlerMethod.getBeanType();
            Method method = handlerMethod.getMethod();
            Annotation[] classAnnotations = aClass.getAnnotations();
            Annotation[] methodAnnotations = method.getAnnotations();
            AccessCount accessCount = null;
            boolean annotationPresent = aClass.isAnnotationPresent(AccessCount.class);
            boolean annotationPresent1 = method.isAnnotationPresent(AccessCount.class);
            if (annotationPresent1){
                accessCount = method.getAnnotation(AccessCount.class);
            }else if (annotationPresent){
                accessCount = aClass.getAnnotation(AccessCount.class);
            }
            boolean value = accessCount.value();
            if (value){
                atomicInteger.incrementAndGet();
            }
        }
        return true;
    }

}
