package com.iptiq.balancer;

import com.iptiq.infra.NoProviderAvailableException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoadBalancerTest {

    @Test
    @DisplayName("Should return a value using dummy strategy")
    void positiveTestWithDummyStrategy() {
        var loadBalancer = createDummyBalancer();
        assertTrue(loadBalancer.register(new Provider()));
        assertNotNull(loadBalancer.get());
    }

    @Test
    @DisplayName("Should throw an exception when no provider is registered")
    void testingAvailableProvider() {
        assertThrows(NoProviderAvailableException.class, () -> createDummyBalancer().get());
    }

    private LoadBalancer createDummyBalancer() {
        return new LoadBalancer(1, (providers) -> providers.stream().findFirst().get());
    }
}