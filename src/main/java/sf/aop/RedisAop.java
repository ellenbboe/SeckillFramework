package sf.aop;

import java.lang.annotation.*;

// TODO: 2020/1/14 aop缓存啥时候做好呢?? 
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface RedisAop {
    String key();
    String value() ;

    long expire() default -1;
}
