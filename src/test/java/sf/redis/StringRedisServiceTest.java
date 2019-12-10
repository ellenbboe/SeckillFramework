package sf.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest

public class StringRedisServiceTest {

    @Autowired
    StringRedisService stringRedisService;
    @Test
    public void setString() {
        stringRedisService.setString("name","张贴表");
    }

    @Test
    public void getString() {
        System.out.println(stringRedisService.getString("name")
        );
    }
}