package sf.others;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sf.dao.OrdMapper;
import sf.entity.Ord;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestInsert {

    @Autowired(required = false)
    OrdMapper ordMapper;

    @Test
    public void insert() {
        Ord ord = new Ord(1,1);
        int a = ordMapper.insertSelective(ord);
        System.out.println(a);
    }
}
