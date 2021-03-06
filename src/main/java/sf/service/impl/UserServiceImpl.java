package sf.service.impl;


import com.alibaba.druid.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sf.cache.RedisCache;
import sf.dao.UserMapper;
import sf.entity.User;
import sf.entity.UserExample;
import sf.exception.BaseException;
import sf.model.UserModel;
import sf.redis.RedisKey;
import sf.redis.RedisService;
import sf.redis.StringRedisService;
import sf.result.CodeMsg;
import sf.service.UserService;
import sf.util.CookieUtil;
import sf.util.JwtUtil;
import sf.vo.LoginVo;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service

public class UserServiceImpl implements UserService {
    @Autowired(required = false)
    UserMapper userMapper;

    @Autowired
    RedisService redisService;
    @Autowired
    StringRedisService stringRedisService;

    //登录
    @Override
    public String login(HttpServletRequest request, LoginVo loginVo) {
        if(loginVo == null)
        {
            throw new BaseException(CodeMsg.LOGIN_OR_PASS_ERROR);
        }
        String token = CookieUtil.getCookieValue(request,CookieUtil.USER_COOKIE_TOKEN_NAME);
        Object[] objects = {token};
        String key = RedisKey.genKey(StringRedisService.class.getName(),"RefreshTokenAndSave",objects);
        String oldRefreshToken = stringRedisService.getString(key);
        if(!StringUtils.isEmpty(token) && !StringUtils.isEmpty(oldRefreshToken) && JwtUtil.canTokenRefresh(token,oldRefreshToken))//过期在前面已经判断过了
        {
            return token;
        }
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();
        ByteSource credentialsSalt = ByteSource.Util.bytes(phone);
        password =new Md5Hash(password,credentialsSalt.toString()).toString();
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andPhoneEqualTo(phone);
        User user = userMapper.selectByExample(userExample).get(0);
        String dbpassword = user.getPassword();
        if(!password.equals(dbpassword) | StringUtils.isEmpty(dbpassword))
        {
            throw new BaseException(CodeMsg.LOGIN_OR_PASS_ERROR);
        }
        stringRedisService.del(key);
        token = JwtUtil.createToken(phone);
        stringRedisService.RefreshTokenAndSave(token);
        return token;
    }


    //使用token得到user
    @RedisCache(TYPE = User.class)
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
        //取缓存
//        String key  = RedisKey.getRedisKey(RedisKey.REDIS_USER_LOGIN_MODEL,RedisKey.REDIS_USER_PAGEMODEL,username);
//        User o  = redisService.getObj(key,User.class);
//        if(o!=null)
//        {
//            return o;
//        }
        //数据库查找
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andPhoneEqualTo(username);
        //放入缓存
//        redisService.setObj(key,result,RedisKey.REDIS_USER_PAGEMODEL_EXPICETIME);
        return userMapper.selectByExample(userExample).get(0);
    }

//    public User GETbyID(){
//        UserExample userExample = new UserExample();
//        UserExample.Criteria criteria = userExample.createCriteria();
//        criteria.andNicknameEqualTo("老王");
//        return userMapper.selectByExample(userExample).get(0);
//    }

    @RedisCache(TYPE = UserModel.class)
    public UserModel usertoModel(User user)
    {
        return new UserModel(user);
    }

}
