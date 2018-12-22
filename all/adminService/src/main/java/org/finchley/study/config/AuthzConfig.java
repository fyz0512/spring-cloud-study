package org.finchley.study.config;

import java.util.Arrays;
import java.util.List;

import org.finchley.study.intercepter.AuthIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthzConfig implements WebMvcConfigurer {
    @Bean
    public AuthIntercepter authIntercepter() {
        return new AuthIntercepter();
    }
    
    @Autowired
    List<String> excludePath;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(authIntercepter());

        // 排除配置
        for(String setting : excludePath)
        addInterceptor.excludePathPatterns(setting);


        // 拦截配置
        addInterceptor.addPathPatterns("/**");
    }
    
    


	@ConfigurationProperties
    class ExcludePathConfig{
    	
    	
    	@Value("${spring.security.excludePaths:\"\"}")
    	private  String setting;
    	
    	
    	@Bean(name="excludePath")
    	public List<String> excludeSetting(){
    		
    		return Arrays.asList(setting.split(","));
    		
    	}
    	
    }
    
}
