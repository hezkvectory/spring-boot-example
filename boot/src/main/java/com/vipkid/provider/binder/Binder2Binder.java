package com.vipkid.provider.binder;

import com.vipkid.provider.registry.MeterRegistry;

public class Binder2Binder implements MeterBinder {
    public void binder(MeterRegistry registry) {

        System.out.println("-----binder2----" + registry);
    }
}
