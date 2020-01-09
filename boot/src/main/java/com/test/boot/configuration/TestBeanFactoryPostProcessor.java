package com.test.boot.configuration;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author hezhengkui.
 * DATE 2020-01-08 11:45.
 */
@Component
public class TestBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    private TestBeanFactoryPostProcessor() {
        System.out.println("TestBeanFactoryPostProcessor.construct");
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

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("TestBeanFactoryPostProcessor.postProcessBeanFactory");
    }
}
