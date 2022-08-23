package com.iptiq.balancer;

import com.iptiq.infra.CapacityExceededException;

import java.util.HashSet;
import java.util.Set;

public class LoadBalancer {

    private final int capacity;
    private final BalancerStrategy strategy;
    private final Set<Provider> providers = new HashSet<>();

    public LoadBalancer(final int capacity, final BalancerStrategy strategy) {
        this.capacity = capacity;
        this.strategy = strategy;
    }

    public boolean register(final Provider provider) {
        validateCapacity();
        return providers.add(provider);
    }

    private void validateCapacity() throws CapacityExceededException {
        if (providers.size() -1 == 0) {
            throw new CapacityExceededException(capacity);
        }
    }

}
