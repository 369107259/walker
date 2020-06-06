package alone.walker.annotation.custom;


import alone.walker.annotation.AccessCount;
import alone.walker.annotation.el.SpelExpressionUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author: huangYong
 * @Date: 2020/3/18 14:40
 */
@Component
@Aspect
public class AccessCountAspect {
    /***
     * 切面扫面所有使用自定义AccessCount注解的地方
     * @param joinPoint
     * @param accessCount
     */
    @Around("@annotation(accessCount)")
    public void doAround(ProceedingJoinPoint joinPoint, AccessCount accessCount) throws Throwable {
        boolean value = accessCount.value();
        String key = accessCount.key();
        //获取el表达式的真实值
        getKey(joinPoint,key);
        joinPoint.proceed();
        //例:打印日志
        System.out.println("类名称==="+joinPoint.getSourceLocation().getWithinType().getName()+"方法名称======"+joinPoint.getSignature().getName());
    }

    private void getKey(ProceedingJoinPoint joinPoint, String key) {
        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        SpelExpressionUtil.getKey(key,parameterNames,joinPoint.getArgs());
    }

}
