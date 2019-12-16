package sf.service;

import sf.entity.User;
import sf.model.UserModel;
import sf.vo.LoginVo;

public interface UserService {
    String login(LoginVo loginVo);
    User getByToken(String token);
    UserModel usertoModel(User user);
}
