package sf.filter;

import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sf.entity.User;
import sf.redis.RedisService;
import sf.util.Const;
import sf.util.CookieUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;



//加长session的时间
//@Component
//@WebFilter(filterName="SessionExpireFilter",urlPatterns="/*")
public class SessionExpireFilter implements Filter {

    @Autowired
    RedisService redisService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String LoginToken = CookieUtil.getCookieValue(request,CookieUtil.USER_COOKIE_TOKEN_NAME);
        if(StringUtils.isEmpty(LoginToken))
        {
            User user = (User)redisService.getObj(LoginToken);
            if(user != null)
            {
                redisService.expice(LoginToken, Const.REDIS_USER_SESSION_TIME);
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
