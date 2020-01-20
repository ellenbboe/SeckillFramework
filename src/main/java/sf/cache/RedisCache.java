package sf.cache;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisCache {
    Class TYPE();
    long Time() default 600;
    TimeUnit TIME_UNIT() default TimeUnit.SECONDS;
}
