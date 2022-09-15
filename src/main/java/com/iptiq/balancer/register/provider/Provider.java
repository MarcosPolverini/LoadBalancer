package com.iptiq.balancer.register.provider;

import java.util.concurrent.atomic.AtomicInteger;

public class Provider {

    private final String id;
    private boolean alive;
    private boolean excluded;
    private int heartBeatCounter;
    private final AtomicInteger maxRequests;
    private final Endpoint endpoint;

    public Provider(final String id, final int maxRequests) {
        this.id = id;
        this.alive = true;
        this.maxRequests = new AtomicInteger(maxRequests);
        this.endpoint = new EndpointSimulator();
    }

    public Provider(final String id, final int maxRequests, final Endpoint endpoint) {
        this.id = id;
        this.alive = true;
        this.maxRequests = new AtomicInteger(maxRequests);
        this.endpoint = endpoint;
    }

    public String get() {
        //Simulate network connection
        var current = maxRequests.decrementAndGet();
        if (current <= 0) {
            throw new IllegalStateException("Provider %s cannot handle more requests".formatted(id));
        }
        var response = endpoint.get();
        maxRequests.incrementAndGet();
        return response;
    }

    public void include() {
        excluded = false;
    }

    public void exclude() {
        excluded = true;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isExcluded() {
        return excluded;
    }

    public void check() {
        //Should not beat excluded provider
        if (excluded) {
            return;
        }
        var checkResult = endpoint.check();
        if (!checkResult) {
            alive = false;
        }
        if (checkResult && ++heartBeatCounter == 2) {
            alive = true;
            heartBeatCounter = 0;
        }
    }


    public int getAvailableCapacity() {
        return maxRequests.get();
    }

    public String getId() {
        return id;
    }

    public boolean isAvailable() {
        return alive && !excluded && maxRequests.get() > 0;
    }

}
