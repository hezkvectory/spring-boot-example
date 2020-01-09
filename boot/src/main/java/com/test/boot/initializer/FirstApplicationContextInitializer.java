package com.test.boot.initializer;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

/**
 * @author hezhengkui.
 * DATE 2020-01-07 16:11.
 */
public class FirstApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    String APOLLO_BOOTSTRAP_PROPERTY_SOURCE_NAME = "ApolloBootstrapPropertySources";

    public void initialize(ConfigurableApplicationContext context) {
        System.out.println("FirstApplicationContextInitializer.initialize");
        ConfigurableEnvironment environment = context.getEnvironment();
        if (environment.getPropertySources().contains(APOLLO_BOOTSTRAP_PROPERTY_SOURCE_NAME)) {
            //already initialized
            return;
        }
        Properties properties = new Properties();
        properties.put("hezk.k", "2");
        PropertiesPropertySource composite = new PropertiesPropertySource(APOLLO_BOOTSTRAP_PROPERTY_SOURCE_NAME, properties);
        environment.getPropertySources().addFirst(composite);
    }
}
