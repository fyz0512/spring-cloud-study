package org.finchley.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("org.finchley.study.dao")
public class AdminApplication 
{
    public static void main( String[] args )
    {
        SpringApplication.run(AdminApplication.class, args);
    }
    
    
    
}
