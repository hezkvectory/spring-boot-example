package com.test.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author hezhengkui.
 * DATE 2020-01-07 16:10.
 */
@SpringBootApplication(scanBasePackages = "com.test.boot")
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
//        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Binder5Binder.class).getBeanDefinition();
//        MyBeanDefinitionRegistryPostProcessor.beanDefinitionRegistry.registerBeanDefinition("Binder5Binder", beanDefinition);

//        System.out.println("=====" + applicationContext.getBean("Binder5Binder"));
//        applicationContext.getBeanFactory();
//        BeanRegistrationUtil.registerBeanDefinitionIfBeanNameNotExists(applicationContext.getBeanFactory(), "Binder5Binder", Binder5Binder.class);
    }
}
