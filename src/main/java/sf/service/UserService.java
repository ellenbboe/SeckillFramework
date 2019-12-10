package sf.service;

import sf.entity.User;
import sf.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

public interface UserService {
    User getById(int id);//没用过
    User getByPhone(String phone);
    boolean login(HttpServletResponse response, LoginVo loginVo);
    User getByToken(String token);
}
