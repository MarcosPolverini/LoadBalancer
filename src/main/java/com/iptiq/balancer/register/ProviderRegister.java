package com.iptiq.balancer.register;

import com.iptiq.balancer.register.provider.Provider;
import com.iptiq.infra.LoadBalancerCapacityExceededException;

import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

public class ProviderRegister implements AutoCloseable {

    private final int capacity;
    private final int providerMaxRequests;
    private final List<Provider> providers = new CopyOnWriteArrayList<>();
    private final Timer timer = new Timer("HeartBeat");

    public ProviderRegister(int capacity, int providerMaxRequests) {
        this.capacity = capacity;
        timer.scheduleAtFixedRate(heartBeat(), 2000L, 2000L);
        this.providerMaxRequests = providerMaxRequests;
    }

    public List<Provider> getProviders() {
        return providers;
    }

    public void register(final String... providers) {
        checkCapacity(providers.length);
        var converted = Stream.of(providers)
                .map(id -> new Provider(id, providerMaxRequests))
                .toList();
        this.providers.addAll(converted);
    }

    public void include(final String providerId) {
        findById(providerId).ifPresentOrElse(Provider::include, () -> register(providerId));
    }

    public void exclude(final String providerId) {
        findById(providerId).ifPresent(Provider::exclude);
    }

    private Optional<Provider> findById(final String providerId) {
        return this.providers.stream().filter(p -> p.getId().equals(providerId)).findFirst();
    }

    private void checkCapacity(int newProviders) {
        if (hasCapacity(newProviders)) {
            throw new LoadBalancerCapacityExceededException(capacity);
        }
    }

    private boolean hasCapacity(final int newProviders) {
        return providers.size() + newProviders > capacity;
    }

    @Override
    public void close() {
        timer.cancel();
    }

    private TimerTask heartBeat() {
        return new TimerTask() {
            @Override
            public void run() {
                providers.forEach(Provider::check);
            }
        };
    }
}
