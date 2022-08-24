package com.iptiq.balancer.register.provider;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class ProviderTest {


    @Test
    @DisplayName("Should throw an exception cannot handle more requests")
    void testingMaxRequest() {
        var provider = new Provider(UUID.randomUUID().toString(), 0);
        assertThrows(IllegalStateException.class, provider::get);
    }

    @Test
    @DisplayName("Should consume a request capacity")
    void testingRequestCapacity() {
        var executors = Executors.newFixedThreadPool(2);
        var provider = new Provider(UUID.randomUUID().toString(), 1);
        executors.execute(provider::get);
        executors.execute(() -> assertEquals(0, provider.getAvailableCapacity()));
        executors.shutdown();
    }

    @Test
    @DisplayName("Should change provider to not alive")
    void testingHeartBeatCheckNotReachable() {
        var provider = new Provider(UUID.randomUUID().toString(), 1, new NegativeEndpoint());
        callForUnavailableProvider(provider);
    }

    @Test
    @DisplayName("Should change provider to alive after two beats")
    void testingRecoveredEndpoint() {
        var provider = new Provider(UUID.randomUUID().toString(), 1, new RecoveredEndpoint());
        callForUnavailableProvider(provider);
        callForUnavailableProvider(provider);
        callForAvailableProvider(provider);
    }

    @Test
    @DisplayName("Should return correct availability excluding and including")
    void testAvailabilityMethod() {
        var provider = new Provider(UUID.randomUUID().toString(), 1);
        assertTrue(provider.isAvailable());
        provider.exclude();
        assertFalse(provider.isAvailable());
        provider.include();
        assertTrue(provider.isAvailable());
    }

    private void callForUnavailableProvider(Provider provider) {
        provider.check();
        assertFalse(provider.isAvailable());
        assertFalse(provider.isAlive());
        assertFalse(provider.isExcluded());
    }

    private void callForAvailableProvider(Provider provider) {
        provider.check();
        assertTrue(provider.isAvailable());
        assertTrue(provider.isAlive());
        assertFalse(provider.isExcluded());
    }


}