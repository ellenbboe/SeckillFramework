package sf.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sf.dao.UserMapper;
import sf.entity.User;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    UserServiceImpl userService;
    @Test
    public void GETbyID(){
        System.out.println(userService.GETbyID().toString());
    }
}