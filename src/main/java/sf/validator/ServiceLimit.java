package sf.validator;


import java.lang.annotation.*;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceLimit {
    String description() default "";
    String key() default "";
    LimitType limitType() default LimitType.CUSTOMER;
    enum LimitType{
        CUSTOMER,
        IP
    }
}
