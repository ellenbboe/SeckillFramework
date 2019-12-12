package sf.service.impl;


import com.alibaba.druid.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sf.dao.UserMapper;
import sf.entity.User;
import sf.exception.BaseException;
import sf.redis.RedisService;
import sf.result.CodeMsg;
import sf.service.UserService;
import sf.util.MD5Util;
import sf.vo.LoginVo;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired(required = false)
    UserMapper userMapper;

    @Autowired
    RedisService redisService;
    @Override
    public String GetpasswordByphone(String phone) {
        return userMapper.GetpasswordByphone(phone);
    }

    @Override
    public User getByPhone(String phone) {
        return userMapper.getByPhone(phone);
    }

    //登录
    @Override
    public boolean login(LoginVo loginVo) {
        Subject subject = SecurityUtils.getSubject();
        if(loginVo == null)
        {
            throw new BaseException(CodeMsg.LOGIN_OR_PASS_ERROR);
        }
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();
        ByteSource credentialsSalt = ByteSource.Util.bytes(phone);
        password =new Md5Hash(password,credentialsSalt.toString()).toString();
        UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
        try{
            subject.login(token);
        } catch (ExcessiveAttemptsException eae) {
            throw new BaseException(CodeMsg.REQUEST_OVER_LIMIT);//请求次数过多
        } catch (AuthenticationException uae) {
            throw new BaseException(CodeMsg.LOGIN_OR_PASS_ERROR);//用户名或者密码错误
        }

        if (!subject.isAuthenticated())
        {
            token.clear();
            return false;
        }
        return true;
    }


    //使用token得到user
    public User getByToken(String token)
    {
        if(StringUtils.isEmpty(token))
        {
            return null;
        }
        return redisService.getObj(token);
    }






}
