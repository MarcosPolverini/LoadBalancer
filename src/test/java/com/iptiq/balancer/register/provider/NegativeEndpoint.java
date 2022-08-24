package com.iptiq.balancer.register.provider;

import com.iptiq.balancer.register.provider.Endpoint;

public class NegativeEndpoint implements Endpoint {

    @Override
    public boolean check() {
        return false;
    }

    @Override
    public String get() {
        return null;
    }
}
