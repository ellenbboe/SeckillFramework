package sf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sf.dao.UserMapper;
import sf.entity.User;
import sf.result.CodeMsg;
import sf.result.Result;
import sf.vo.LoginVo;

public interface UserService {
    User getById(int id);//没用过
    User getByPhone(String phone);
    boolean login(LoginVo loginVo);
}
