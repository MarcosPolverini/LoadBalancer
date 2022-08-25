package com.iptiq.balancer;

import com.iptiq.balancer.register.provider.Provider;

import java.util.logging.Logger;

public class RoundRobinLoadBalancerImpl extends AbstractLoadBalancer {

    private int lastIndex;
    private final Object lock = new Object();
    private static final Logger LOG = Logger.getLogger(RoundRobinLoadBalancerImpl.class.getName());

    public RoundRobinLoadBalancerImpl(int maxRequestPerProvider) {
        super(maxRequestPerProvider);
    }

    @Override
    public String get() {
        var providers = getProviders();
        checkClusterCapacity(providers);
        Provider provider;
        synchronized (lock) {
            do {
                provider = providers.get(lastIndex);
                lastIndex = (lastIndex + 1) % providers.size();
            } while (!provider.isAvailable());
            LOG.info("Selected provider %s ".formatted(provider.getId()));
        }
        return provider.get();
    }
}
