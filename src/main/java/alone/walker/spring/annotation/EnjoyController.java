package alone.walker.spring.annotation;

import java.lang.annotation.*;

/**
 * @Author: huangYong
 * @Date: 2020/4/15 14:17
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnjoyController {
    String value() default "";
}
