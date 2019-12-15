package sf.service.impl;


import com.alibaba.druid.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sf.dao.UserMapper;
import sf.entity.User;
import sf.entity.UserExample;
import sf.exception.BaseException;
import sf.redis.RedisService;
import sf.result.CodeMsg;
import sf.service.UserService;
import sf.util.JwtUtil;
import sf.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    public String login(LoginVo loginVo) {
        if(loginVo == null)
        {
            throw new BaseException(CodeMsg.LOGIN_OR_PASS_ERROR);
        }
        Subject subject = SecurityUtils.getSubject();
        System.out.println("login");
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();
        ByteSource credentialsSalt = ByteSource.Util.bytes(phone);
        password =new Md5Hash(password,credentialsSalt.toString()).toString();
//        UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
//        subject.login(token);
        if(!password.equals(GetpasswordByphone(phone)) | StringUtils.isEmpty(GetpasswordByphone(phone)))
        {
            throw new BaseException(CodeMsg.LOGIN_OR_PASS_ERROR);
        }
        return JwtUtil.createToken(phone);
    }


    //使用token得到user
    public User getByToken(String token)
    {
        if(StringUtils.isEmpty(token))
        {
            return null;
        }
        String username = JwtUtil.getUsername(token);
        if (username == null || !JwtUtil.verify(token, username)) {
            throw new AuthenticationException("token认证失败！");
        }
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andPhoneEqualTo(username);
        return userMapper.selectByExample(userExample).get(0);
    }

    public User GETbyID(){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andNicknameEqualTo("老王");
        return userMapper.selectByExample(userExample).get(0);
    }
}
