package alone.walker.spring.annotation;

import java.lang.annotation.*;

/**
 * @Author: huangYong
 * @Date: 2020/4/15 14:28
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnjoyAutowired {
    String value() default "";
}
