package sf.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import sf.dao.UserMapper;
import sf.entity.User;
import sf.exception.BaseException;
import sf.result.CodeMsg;
import sf.service.UserService;
import sf.util.MD5Util;
import sf.vo.LoginVo;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    UserMapper userMapper;
    @Override
    public User getById(int id) {
        return userMapper.getById(id);
    }

    @Override
    public User getByPhone(String phone) {
        return userMapper.getByPhone(phone);
    }

    @Override
    public boolean login(LoginVo loginVo) {
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
        return true;

    }


}
