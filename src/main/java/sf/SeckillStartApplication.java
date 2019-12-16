package sf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("sf.dao")
//@EnableCaching
@ServletComponentScan
public class SeckillStartApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(SeckillStartApplication.class,args);
    }
}
