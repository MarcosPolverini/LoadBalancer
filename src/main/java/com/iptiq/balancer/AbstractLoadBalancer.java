package com.iptiq.balancer;

import com.iptiq.balancer.register.ProviderRegister;
import com.iptiq.balancer.register.provider.Provider;
import com.iptiq.infra.ClusterCapacityExceededException;
import com.iptiq.infra.NoProviderAvailableException;

import java.util.List;

abstract class AbstractLoadBalancer implements LoadBalancer {

    private static final int DEFAULT_CAPACITY = 10;

    protected AbstractLoadBalancer(final int maxRequestPerProvider) {
        this.providerRegister = new ProviderRegister(DEFAULT_CAPACITY, maxRequestPerProvider);
    }

    private final ProviderRegister providerRegister;

    @Override
    public void register(final String... providersIds) {
        providerRegister.register(providersIds);
    }

    @Override
    public void exclude(final String providerId) {
        providerRegister.exclude(providerId);
    }

    @Override
    public void include(final String providerId) {
        providerRegister.include(providerId);
    }

    protected List<Provider> getAvailableProviders() {
        var available = providerRegister.getProviders().stream().filter(Provider::isAvailable).toList();
        if (available.isEmpty()) {
            throw new NoProviderAvailableException();
        }
        checkClusterCapacity(available);
        return available;
    }

    private void checkClusterCapacity(final List<Provider> available) {
        var totalCapacity = available.stream().mapToLong(Provider::getAvailableCapacity).sum();
        if (totalCapacity - 1 < 0) {
            throw new ClusterCapacityExceededException();
        }
    }

    @Override
    public void close() {
        this.providerRegister.close();
    }
}
