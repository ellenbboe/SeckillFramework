package sf.shiro;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sf.filter.JwtFilter;
import sf.jwt.JwtDefaultSubjectFactory;
import sf.service.UserService;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class shiroConfig {
    @Bean
    JwtDefaultSubjectFactory subjectFactory()
    {
        return new JwtDefaultSubjectFactory();
    }
    @Bean
    ShiroRealm ShiroRealm() {
        return new ShiroRealm();
    }
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(ShiroRealm());
        // 关闭 ShiroDAO 功能
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        // 不需要将 Shiro Session 中的东西存到任何地方（包括 Http Session 中）
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        //禁止Subject的getSession方法
        securityManager.setSubjectFactory(subjectFactory());
        return securityManager;
    }
//    @Bean
//    ShiroFilterChainDefinition shiroFilterChainDefinition() {
//        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
//        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        filterChainDefinitionMap.put("/**/*.js","anon");
//        filterChainDefinitionMap.put("/**/*.css","anon");
//        filterChainDefinitionMap.put("/**/*.jpg","anon");
//        filterChainDefinitionMap.put("/**/*.png","anon");
//        filterChainDefinitionMap.put("/user/do_login", "anon");
//        filterChainDefinitionMap.put("/static/","anon");
//        filterChainDefinitionMap.put("/**", "authc");
//        definition.addPathDefinitions(filterChainDefinitionMap);
//        return definition;
//    }


    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager());
        shiroFilter.setLoginUrl("/user/login");
        shiroFilter.setUnauthorizedUrl("/user/login");
        /*
         * c. 添加jwt过滤器，并在下面注册
         * 也就是将jwtFilter注册到shiro的Filter中
         * 指定除了login和logout之外的请求都先经过jwtFilter
         * */
        Map<String, Filter> filterMap = shiroFilter.getFilters();
        //这个地方其实另外两个filter可以不设置，默认就是
        filterMap.put("jwt", new JwtFilter());
        shiroFilter.setFilters(filterMap);

        // 拦截器
        Map<String, String> filterRuleMap = new LinkedHashMap<>();
        filterRuleMap.put("/user/login", "anon");
        filterRuleMap.put("/**/*.js","anon");
        filterRuleMap.put("/**/*.css","anon");
        filterRuleMap.put("/**/*.jpg","anon");
        filterRuleMap.put("/**/*.png","anon");
        filterRuleMap.put("/static/","anon");
        filterRuleMap.put("/**", "jwt");
        shiroFilter.setFilterChainDefinitionMap(filterRuleMap);
        return shiroFilter;
    }

}
