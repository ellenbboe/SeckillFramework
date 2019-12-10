package sf.service.impl;


import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sf.dao.UserMapper;
import sf.entity.User;
import sf.exception.BaseException;
import sf.redis.RedisService;
import sf.result.CodeMsg;
import sf.service.UserService;
import sf.util.CookieUtil;
import sf.util.MD5Util;
import sf.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl implements UserService {


    @Autowired(required = false)
    UserMapper userMapper;

    @Autowired
    RedisService redisService;
    @Override
    public User getById(int id) {
        return userMapper.getById(id);
    }

    @Override
    public User getByPhone(String phone) {
        return userMapper.getByPhone(phone);
    }

    //登录
    @Override
    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if(loginVo == null)
        {
            throw new BaseException(CodeMsg.SERVER_ERROR);
        }
        String phone = loginVo.getPhone();
        User user = getByPhone(phone);
        if(user == null)
        {
            throw new BaseException(CodeMsg.LOGIN_ERROR_USER_NOT_ERROR);
        }
        String salt = user.getSalt();
        String password = MD5Util.FormPassToDB(loginVo.getPassword(),salt);
        if(!password.equals(user.getPassword()))
        {
            throw new BaseException(CodeMsg.LOGIN_ERROR_PASS_ERROR);
        }
        // TODO: 2019/12/10 这里不应该使用redis 
        redisService.addTokenInCookie(response,user);
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
