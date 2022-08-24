package com.iptiq.balancer.register;

import com.iptiq.infra.LoadBalancerCapacityExceededException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class ProviderRegisterTest {

    private final String[] providers = {UUID.randomUUID().toString(), UUID.randomUUID().toString()};

    @Test
    @DisplayName("Should register all providers")
    void testingRegister() {
        testRegister(register -> {
            register.register(providers);
            assertEquals(2, register.getProviders().size());
        });
    }

    @Test
    @DisplayName("Should exclude a provider")
    void testingExclusions() {
        testRegister(register -> {
            register.exclude(providers[0]);
            assertTrue(register.getProviders().get(0).isExcluded());
        }, providers);
    }

    @Test
    @DisplayName("Should include a provider")
    void testingInclusion() {
        testRegister(register -> {
            register.getProviders().get(0).exclude();
            register.include(providers[0]);
            assertFalse(register.getProviders().get(0).isExcluded());
        }, providers);
    }

    @Test
    @DisplayName("Should throw an exception for load balancer capacity exceeded of providers")
    void testingMaxCapacity() {
        testRegister(register -> {
            assertThrows(LoadBalancerCapacityExceededException.class, () -> register.register("", "", ""));
        });
    }

    private void testRegister(Consumer<ProviderRegister> consumer, String... providers) {
        try (var register = new ProviderRegister(2, 2)) {
            if (providers != null) {
                register.register(providers);
            }
            consumer.accept(register);
        }
    }


}