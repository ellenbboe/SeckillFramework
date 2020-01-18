package sf.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sf.service.OrderService;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest

public class OrderServiceImplTest {

    @Autowired
    OrderService orderService;
    @Test
    public void getById() {
        orderService.GetById("413841252329590784");
    }
}
