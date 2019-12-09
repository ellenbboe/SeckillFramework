package sf.dao;


import org.apache.ibatis.annotations.Mapper;
import sf.entity.User;

public interface UserMapper {
    User getById(int id);
    User getByPhone(String phone);
//    User selectByPhoneAndPassword(@Param("phone") String phone , @Param("password") String password);
//
//    User checkPhone(@Param("phone") String phone );
}
