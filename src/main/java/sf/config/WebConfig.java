package sf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;
import sf.Resolver.TokenResolver;
import sf.interceptor.LoginInterceptor;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    TokenResolver tokenResolver;

    //拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] excludes = new String[]{"/","/user/do_login","/user/login","/static/**","/templates/**","/**/*.css",
                "/**/*.js", "/**/*.png", "/**/*.jpg","/**/*.jpeg", "/**/*.gif", "/**/fonts/*"};
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludes);
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
//        WebMvcConfigurer.super.addResourceHandlers(registry);
//    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(tokenResolver);
    }

//解决跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
                .allowCredentials(true) //设置为true
                .maxAge(3600);;
    }
}
