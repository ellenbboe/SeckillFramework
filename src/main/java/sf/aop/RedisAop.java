package sf.aop;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface RedisAop {
    String key();
    String value() ;

    long expire() default -1;
}
