package sf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("sf.dao")
@EnableCaching
@ServletComponentScan
@EnableAspectJAutoProxy
public class SeckillStartApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(SeckillStartApplication.class,args);
    }
}
