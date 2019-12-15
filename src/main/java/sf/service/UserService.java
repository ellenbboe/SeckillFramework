package sf.service;

import sf.entity.User;
import sf.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

public interface UserService {
    String GetpasswordByphone(String phone);
    User getByPhone(String phone);
    String login(LoginVo loginVo);
    User getByToken(String token);
}
