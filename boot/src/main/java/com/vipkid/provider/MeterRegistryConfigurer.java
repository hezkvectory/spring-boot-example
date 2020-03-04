package com.vipkid.provider;

import com.vipkid.provider.binder.MeterBinder;
import com.vipkid.provider.registry.MeterRegistry;
import org.springframework.beans.factory.ObjectProvider;

public class MeterRegistryConfigurer {
    ObjectProvider<MeterBinder> meterBinders;

    public MeterRegistryConfigurer(ObjectProvider<MeterBinder> meterBinders) {
        this.meterBinders = meterBinders;
    }

    public void configure(MeterRegistry registry) {
        this.meterBinders.orderedStream().forEach((binder) -> binder.binder(registry));
    }
}
