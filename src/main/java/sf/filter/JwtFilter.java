package sf.filter;


import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import sf.exception.BaseException;
import sf.jwt.JwtToken;
import sf.redis.RedisKey;
import sf.redis.StringRedisService;
import sf.result.CodeMsg;
import sf.util.CookieUtil;
import sf.util.JwtUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtFilter extends BasicHttpAuthenticationFilter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StringRedisService stringRedisService;

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws UnauthorizedException {
        //判断请求的请求头是否带上 "token"
        if (isLoginAttempt(servletRequest, servletResponse)) {
            //如果存在，则进入 executeLogin 方法执行登入，检查 token 是否正确
            try {
                executeLogin(servletRequest, servletResponse);
                return true;
            } catch (Exception e) {
                //token 错误
                responseError(servletResponse);
                return false;
            }
        }
        //如果请求头不存在 token，则可能是执行登陆操作或者是游客状态访问，无需检查 token，直接返回 true
        return true;
    }

    /**
     * 判断用户是否想要登入。
     * 检测 header 里面是否包含 token 字段
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = CookieUtil.getCookieValue(req,"token");
        return token != null;
    }

    /**
     * 执行登陆操作
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response){
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String token = CookieUtil.getCookieValue(httpServletRequest,"token");
        if(JwtUtil.isTokenExpired(token))
        {
            if(!refreshToken(httpServletRequest,httpServletResponse,token))
            {
                throw new BaseException(CodeMsg.TOKEN_EXPIRED);
            }
        }else{
            JwtToken jwtToken = new JwtToken(token);
            // 提交给realm进行登入，如果错误它会抛出异常并被捕获
            getSubject(request, response).login(jwtToken);
            // 如果没有抛出异常则代表登入成功，返回true
        }

        return true;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 将非法请求跳转到 /user/login界面*
     */
    private void responseError(ServletResponse response) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            //设置编码，否则中文字符在重定向时会变为空字符串
//            message = URLEncoder.encode(message, "UTF-8");
            CookieUtil.delTokenCookies(httpServletResponse);
            httpServletResponse.sendRedirect("/user/login");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }

    /**
     * token已经过期
     * @param request
     * @param response
     * @return
     */
    private boolean refreshToken(HttpServletRequest request, HttpServletResponse response,String token)
    {
        if(stringRedisService.exits(token))
        {
            System.out.println("刷新token!!!!!!!!!!!!!!!!!!!!");
            //获取refresh
            Object[] objects={token};
            String key = RedisKey.genKey(StringRedisService.class.getName(),"RefreshTokenAndSave",objects);
            String refreshToken = stringRedisService.getString(key);
//            String refreshToken1 =
            if(JwtUtil.canTokenRefresh(token,refreshToken))
            {
                stringRedisService.del(key);
                String newToken = JwtUtil.createToken(JwtUtil.getUsername(token));
                stringRedisService.RefreshTokenAndSave(newToken);
                JwtToken jwtToken = new JwtToken(newToken);
                getSubject(request,response).login(jwtToken);
                CookieUtil.setCookieValue(request,response,"token",newToken);
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

}
