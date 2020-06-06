package alone.walker.spring.annotation;

import java.lang.annotation.*;

/**
 * @Author: huangYong
 * @Date: 2020/4/15 14:26
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnjoyRequestMapping {
    String value() default "";
}
