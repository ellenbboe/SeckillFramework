package sf.service;

import sf.entity.User;
import sf.vo.LoginVo;

public interface UserService {
    String GetpasswordByphone(String phone);
    User getByPhone(String phone);
    boolean login(LoginVo loginVo);
    User getByToken(String token);
}
