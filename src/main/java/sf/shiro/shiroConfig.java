package sf.shiro;

import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class shiroConfig {
    @Bean
    ShiroRealm ShiroRealm() {
        return new ShiroRealm();
    }
    @Bean
    DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(ShiroRealm());
        return manager;
    }
    @Bean
    ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/**/*.js","anon");
        filterChainDefinitionMap.put("/**/*.css","anon");
        filterChainDefinitionMap.put("/**/*.jpg","anon");
        filterChainDefinitionMap.put("/**/*.png","anon");
        filterChainDefinitionMap.put("/user/do_login", "anon");
        filterChainDefinitionMap.put("/static/","anon");
        filterChainDefinitionMap.put("/**", "authc");
        definition.addPathDefinitions(filterChainDefinitionMap);
        return definition;
    }
}
