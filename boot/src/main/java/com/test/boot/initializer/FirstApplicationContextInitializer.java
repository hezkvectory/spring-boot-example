package com.test.boot.initializer;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author hezhengkui.
 * DATE 2020-01-07 16:11.
 */
public class FirstApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println("FirstApplicationContextInitializer.initialize");
    }
}
