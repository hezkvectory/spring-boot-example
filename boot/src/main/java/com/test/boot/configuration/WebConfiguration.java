package com.test.boot.configuration;

import com.test.boot.bean.Bean1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hezhengkui.
 * DATE 2020-01-07 17:02.
 */
@Configuration
public class WebConfiguration {
    @Bean
//    @ConditionalOnProperty(value = "hezk.k", havingValue = "1")
    Bean1 bean1() {
        return new Bean1();
    }
}
