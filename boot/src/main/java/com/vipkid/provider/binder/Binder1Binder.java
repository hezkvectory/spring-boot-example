package com.vipkid.provider.binder;

import com.vipkid.provider.registry.MeterRegistry;

public class Binder1Binder implements MeterBinder {
    public void binder(MeterRegistry registry) {
        System.out.println("-----binder1----" + registry);
    }
}
