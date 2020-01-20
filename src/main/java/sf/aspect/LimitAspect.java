package sf.aspect;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sf.exception.BaseException;
import sf.result.CodeMsg;
import sf.util.IpUtils;
import sf.validator.ServiceLimit;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class LimitAspect {
    private static LoadingCache<String, RateLimiter> caches = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<String,RateLimiter>(){
                @Override
                public RateLimiter load(String key){
                    return RateLimiter.create(100);
                }
            });

    @Pointcut("@annotation(sf.validator.ServiceLimit)")
    public void ServiceAspect(){}

    @Around("ServiceAspect()")
    public Object around(ProceedingJoinPoint joinPoint){
        log.info("调用了方法");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ServiceLimit limitAnnotation = method.getAnnotation(ServiceLimit.class);
        ServiceLimit.LimitType limitType = limitAnnotation.limitType();
        String key = limitAnnotation.key();
        Object obj;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        try{
            if(limitType.equals(ServiceLimit.LimitType.IP))
            {
                key = IpUtils.getIpAddr(request);
                log.info(key);
            }
            RateLimiter rateLimiter = caches.get(key);
            boolean flag = rateLimiter.tryAcquire();
            if(flag)
            {
                obj = joinPoint.proceed();
            }else{
                throw new BaseException(CodeMsg.REQUEST_OVER_LIMIT);
            }
        }catch (Throwable e)
        {
            e.printStackTrace();
            throw new BaseException(CodeMsg.REQUEST_OVER_LIMIT);
        }
        return obj;
    }
}
