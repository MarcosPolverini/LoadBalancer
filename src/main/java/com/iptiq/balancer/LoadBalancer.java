package com.iptiq.balancer;

import com.iptiq.infra.CapacityExceededException;
import com.iptiq.infra.NoProviderAvailableException;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LoadBalancer {

    private final int capacity;
    private final BalancerStrategy strategy;
    private final Set<Provider> providers = ConcurrentHashMap.newKeySet();

    public LoadBalancer(final int capacity, final BalancerStrategy strategy) {
        this.capacity = capacity;
        this.strategy = strategy;
    }

    public String get() {
        if (providers.isEmpty()) {
            throw new NoProviderAvailableException();
        }
        return strategy.get(providers).get();
    }

    public boolean register(final Provider provider) {
        validateCapacity();
        return providers.add(provider);
    }

    private void validateCapacity() throws CapacityExceededException {
        if (providers.size() - 1 == 0) {
            throw new CapacityExceededException(capacity);
        }
    }

}
