package sf.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sf.jwt.JwtToken;
import sf.service.UserService;
import sf.util.JwtUtil;


@Component
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    /*
     * 多重写一个support
     * 标识这个Realm是专门用来验证JwtToken
     * 不负责验证其他的token（UsernamePasswordToken）
     * */
    @Override
    public boolean supports(AuthenticationToken token) {
        //这个token就是从过滤器中传入的jwtToken
        return token instanceof JwtToken;
    }

    /**
    没有权限的控制,所以不进行验证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     ShiroRealm中用户验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String jwt = (String) authenticationToken.getPrincipal();
        String username = JwtUtil.getUsername(jwt);
        System.out.println("ShiroRealm中用户验证");
        if (username == null || !JwtUtil.verify(jwt, username)) {
            throw new AuthenticationException("token认证失败！");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(jwt,jwt,getName());
        return info;
    }
}
