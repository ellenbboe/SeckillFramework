package sf.Resolver;

import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import sf.entity.User;
import sf.service.UserService;
import sf.util.CookieUtil;
import sf.validator.LoginTokenValidator;

import javax.servlet.http.HttpServletRequest;

//通过token来获取user
@Component
public class TokenResolver implements HandlerMethodArgumentResolver {

    @Autowired
    UserService userService;
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        if(methodParameter.hasParameterAnnotation(LoginTokenValidator.class)){
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);

        String paramToken = request.getParameter(CookieUtil.USER_COOKIE_TOKEN_NAME);
        String cookieToken = CookieUtil.getCookieValue(request,CookieUtil.USER_COOKIE_TOKEN_NAME);

        if(StringUtils.isEmpty(paramToken)&&StringUtils.isEmpty(cookieToken))
        {
            return null;
        }
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        return userService.getByToken(token);
    }
}
