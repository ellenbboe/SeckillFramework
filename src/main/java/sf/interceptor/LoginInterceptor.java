package sf.interceptor;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.web.servlet.HandlerInterceptor;
import sf.result.CodeMsg;
import sf.result.Result;
import sf.util.CookieUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(StringUtils.isEmpty(CookieUtil.getCookieValue(request,CookieUtil.USER_COOKIE_TOKEN_NAME)))
        {
            render(response, CodeMsg.LOGIN_ERROR_USER_NOT_ERROR);
        }


        return false;
    }

    private void render(HttpServletResponse response, CodeMsg cm)throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str  = JSON.toJSONString(Result.error(cm));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}
