package com.iptiq.balancer;

public interface LoadBalancer extends AutoCloseable{

    String get();

    void register(final String... providers);

    void exclude(final String providerId);

    void include(final String providerId);

}