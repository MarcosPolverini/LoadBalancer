package com.iptiq.balancer;

import com.iptiq.infra.ClusterCapacityExceededException;
import com.iptiq.infra.NoProviderAvailableException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertThrows;

abstract class AbstractLoadBalancerTest {

    @Test
    @DisplayName("Should throw an exception when no providers are available")
    void testingNoAvailableProvider() {
        testBalancer(1, loadBalancer -> {
            var provider = UUID.randomUUID().toString();
            loadBalancer.register(provider);
            loadBalancer.exclude(provider);
            assertThrows(NoProviderAvailableException.class, loadBalancer::get);
        });
    }

    @Test
    @DisplayName("Should throw an exception when the cluster capacity is exceeded")
    void testingClusterCapacity() {
        testBalancer(0, loadBalancer -> {
            var provider = UUID.randomUUID().toString();
            loadBalancer.register(provider);
            assertThrows(ClusterCapacityExceededException.class, loadBalancer::get);
        });
    }

    protected void testBalancer(int maxRequestPerProvider, Consumer<LoadBalancer> test) {
        try (var loadBalancer = produce(maxRequestPerProvider)) {
            test.accept(loadBalancer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    abstract LoadBalancer produce(int maxRequests);

}
