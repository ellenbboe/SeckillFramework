package sf.service;

import sf.entity.User;
import sf.model.UserModel;
import sf.vo.LoginVo;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    String login(HttpServletRequest request, LoginVo loginVo);
    User getByToken(String token);
    UserModel usertoModel(User user);
}
