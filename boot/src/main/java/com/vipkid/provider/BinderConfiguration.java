package com.vipkid.provider;

import com.vipkid.provider.binder.Binder4Binder;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter({MeterRegistryAutoConfiguration.class})
public class BinderConfiguration {

    public BinderConfiguration(){
        System.out.println("BinderConfiguration.BinderConfiguration");
    }

    @Bean
    Binder4Binder binder4Binder() {
        System.out.println("------binder4");
        return new Binder4Binder();
    }

}
