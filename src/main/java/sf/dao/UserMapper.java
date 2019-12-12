package sf.dao;


import org.apache.ibatis.annotations.Mapper;
import sf.entity.User;

public interface UserMapper {
    String GetpasswordByphone(String phone);
    User getByPhone(String phone);
//    User selectByPhoneAndPassword(@Param("phone") String phone , @Param("password") String password);
//
//    User checkPhone(@Param("phone") String phone );
}
