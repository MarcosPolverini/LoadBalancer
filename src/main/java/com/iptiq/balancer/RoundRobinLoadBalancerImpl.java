package com.iptiq.balancer;

import com.iptiq.balancer.register.provider.Provider;

public class RoundRobinLoadBalancerImpl extends AbstractLoadBalancer {

    private int lastIndex;
    private final Object lock = new Object();

    public RoundRobinLoadBalancerImpl(int maxRequestPerProvider) {
        super(maxRequestPerProvider);
    }

    @Override
    public String get() {
        var available = getAvailableProviders();
        Provider provider;
        synchronized (lock) {
            if (lastIndex > available.size() - 1) {
                lastIndex = 0;
            }
            provider = available.get(lastIndex);
            lastIndex = lastIndex + 1 % available.size();
        }
        return provider.get();
    }
}
