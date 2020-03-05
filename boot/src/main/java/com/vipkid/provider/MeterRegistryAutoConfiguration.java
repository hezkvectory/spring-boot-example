package com.vipkid.provider;

import com.vipkid.provider.binder.MeterBinder;
import com.vipkid.provider.registry.MeterRegistry;
import com.vipkid.provider.registry.TestMeterRegistry;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
//@AutoConfigureAfter({BinderAutoConfiguration.class})
public class MeterRegistryAutoConfiguration {

    public MeterRegistryAutoConfiguration(){
        System.out.println("MeterRegistryAutoConfiguration.MeterRegistryAutoConfiguration");
    }

    @Bean
    public MeterRegistry registry(ApplicationContext applicationContext, List<MeterBinder> meterBinders) {
        System.out.println("----00000");
        System.out.println(meterBinders);
        return new TestMeterRegistry();
    }

    @Bean
    MeterRegistryPostProcessor meterRegistryPostProcessor(ObjectProvider<MeterBinder> binders, ApplicationContext applicationContext){
        System.out.println("-------registry---begin----" + binders);
        binders.orderedStream().forEach((binder) -> {
            System.out.println(binder);
        });
        System.out.println("-------registry----end----");
//        Map<String, MeterBinder> beans = applicationContext.getBeansOfType(MeterBinder.class);
//        System.out.println(beans);
        return new MeterRegistryPostProcessor(binders, applicationContext);
    }

}
