package alone.walker.spring.annotation;

import java.lang.annotation.*;

/**
 * @Author: huangYong
 * @Date: 2020/4/15 14:30
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnjoyRequestParam {
    String value() default "";
}
