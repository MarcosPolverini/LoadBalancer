package com.iptiq.balancer.register.provider;

import com.iptiq.balancer.register.provider.Endpoint;

public class RecoveredEndpoint implements Endpoint {

    private boolean called;

    @Override
    public boolean check() {
        if (called) {
            return true;
        }
        called = true;
        return false;
    }

    @Override
    public String get() {
        return null;
    }
}
