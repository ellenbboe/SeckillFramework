package sf.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import sf.service.UserService;

import java.util.Arrays;


public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password = userService.GetpasswordByphone(username);
        if (null == password) {
            throw new AuthenticationException("doGetAuthenticationInfo中的用户名不对");
        } else if (!password.equals(String.valueOf(token.getPassword()))){
            throw new AuthenticationException("doGetAuthenticationInfo中的密码不对");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username,password,getName());
        return info;
    }
}
