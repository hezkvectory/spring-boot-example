package com.vipkid.provider;

import com.vipkid.provider.binder.Binder1Binder;
import com.vipkid.provider.binder.Binder2Binder;
import com.vipkid.provider.binder.Binder3Binder;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore({MeterRegistryAutoConfiguration.class})
//@AutoConfigureAfter({MeterRegistryAutoConfiguration.class})
public class BinderAutoConfiguration {

    public BinderAutoConfiguration(){
        System.out.println("BinderAutoConfiguration.BinderAutoConfiguration");
    }

    @Bean
    Binder1Binder binder1Binder() {
        System.out.println("------binder1");
        return new Binder1Binder();
    }

    @Bean
    Binder2Binder Binder2Binder() {
        System.out.println("------binder2");
        return new Binder2Binder();
    }

    @Bean
    Binder3Binder Binder3Binder() {
        System.out.println("------binder3");
        return new Binder3Binder();
    }
}
