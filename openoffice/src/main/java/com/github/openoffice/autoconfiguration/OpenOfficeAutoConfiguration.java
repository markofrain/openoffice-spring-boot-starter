package com.github.openoffice.autoconfiguration;

import com.github.openoffice.OpenOfficeStartUp;
import com.github.openoffice.OpenOffice;
import com.github.openoffice.properties.OpenOfficeProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
* openoffice自动配置
* @author cgq_r
* @date 2020/9/18
*/
@EnableConfigurationProperties(value = {OpenOfficeProperties.class})
@Configuration
public class OpenOfficeAutoConfiguration {


    @Bean
    @ConditionalOnProperty(prefix = "com.github.open-office",name = {"enable"},havingValue = "true")
    @Order(9999)
    public OpenOfficeStartUp openOfficeStartUp(OpenOfficeProperties properties){
        return new OpenOfficeStartUp(properties);
    }

    @Bean
    public OpenOffice openOffice(OpenOfficeProperties properties){
        return new OpenOffice(properties);
    }
}
