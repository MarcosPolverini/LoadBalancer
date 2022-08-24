package com.iptiq.balancer;

import java.util.concurrent.ThreadLocalRandom;

public final class RandomLoadBalancerImpl extends AbstractLoadBalancer {

    public RandomLoadBalancerImpl(int maxRequestPerProvider) {
        super(maxRequestPerProvider);
    }
    @Override
    public String get() {
        var available = getAvailableProviders();
        final var random = ThreadLocalRandom.current().nextInt(available.size());
        return available.get(random).get();
    }

}
