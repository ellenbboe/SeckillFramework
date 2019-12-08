package sf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("sf.dao")
public class SeckillStartApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(SeckillStartApplication.class,args);
    }
}
