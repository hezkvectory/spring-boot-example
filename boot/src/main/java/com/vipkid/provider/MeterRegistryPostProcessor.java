
package com.vipkid.provider;

import com.vipkid.provider.binder.MeterBinder;
import com.vipkid.provider.registry.MeterRegistry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;

class MeterRegistryPostProcessor implements BeanPostProcessor {

	private final ObjectProvider<MeterBinder> meterBinders;

	private volatile MeterRegistryConfigurer configurer;

	private final ApplicationContext applicationContext;

	public MeterRegistryPostProcessor(ObjectProvider<MeterBinder> meterBinders, ApplicationContext applicationContext) {
		this.meterBinders = meterBinders;
		this.applicationContext = applicationContext;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof MeterRegistry) {
            getConfigurer().configure((MeterRegistry) bean);
		}
		return bean;
	}

	private MeterRegistryConfigurer getConfigurer() {
		if (this.configurer == null) {
			this.configurer = new MeterRegistryConfigurer(this.meterBinders);
		}
		return this.configurer;
	}

}
