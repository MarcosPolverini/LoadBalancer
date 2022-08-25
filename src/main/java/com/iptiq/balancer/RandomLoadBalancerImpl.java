package com.iptiq.balancer;

import com.iptiq.balancer.register.provider.Provider;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public final class RandomLoadBalancerImpl extends AbstractLoadBalancer {

    private static final Logger LOG = Logger.getLogger(RandomLoadBalancerImpl.class.getName());

    public RandomLoadBalancerImpl(int maxRequestPerProvider) {
        super(maxRequestPerProvider);
    }

    @Override
    public String get() {
        var available = getProviders().stream().filter(Provider::isAvailable).toList();
        checkClusterCapacity(available);
        Provider provider;
        do {
            final var random = ThreadLocalRandom.current().nextInt(available.size());
            provider = available.get(random);
        } while (!provider.isAvailable());
        LOG.info("Random pick provider %s".formatted(provider.getId()));
        return provider.get();
    }

}
