package com.iptiq.balancer.register.provider;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

final class EndpointSimulator implements Endpoint{

    public boolean check() {
        sleep(150L);
        return ThreadLocalRandom.current().nextBoolean();
    }

    public String get() {
        sleep(200L);
        return UUID.randomUUID().toString();
    }


    private static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            //Ignore exception, it's used for simulation
        }
    }

}
