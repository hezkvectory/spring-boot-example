package com.test.boot.configuration;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import javax.annotation.PostConstruct;

/**
 * @author hezhengkui.
 * DATE 2020-01-08 11:45.
 */
@Component
public class Test implements InstantiationAwareBeanPostProcessor,ApplicationContextAware, InitializingBean, EnvironmentAware, ResourceLoaderAware, EmbeddedValueResolverAware, ApplicationEventPublisherAware, MessageSourceAware, BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, BeanPostProcessor {
    private Test() {
        System.out.println("test.construct");
    }

    private int number;

    public void setNumber(int number) {
        this.number = number;
        System.out.println("test.number");
    }

    @PostConstruct
    public void post() {
        System.out.println("Test.post");
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("Test.afterPropertiesSet");
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("Test.setApplicationContext");
    }

    public void setEnvironment(Environment environment) {
        System.out.println("Test.setEnvironment");
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        System.out.println("Test.setResourceLoader");
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        System.out.println("Test.setApplicationEventPublisher");
    }

    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        System.out.println("Test.setEmbeddedValueResolver");
    }

    public void setMessageSource(MessageSource messageSource) {
        System.out.println("Test.setMessageSource");
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("Test.setBeanClassLoader");
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("Test.setBeanFactory");
    }

    public void setBeanName(String name) {
        System.out.println("Test.setBeanName");
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("Test.postProcessBeforeInitialization");
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("Test.postProcessAfterInitialization");
        return bean;
    }

    //postProcessBeforeInitialization
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.out.println("Test.postProcessBeforeInstantiation");
        return null;
    }

    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        System.out.println("Test.postProcessAfterInstantiation");
        return false;
    }

    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        System.out.println("Test.postProcessProperties");
        return null;
    }
}
