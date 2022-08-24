package com.iptiq;

import com.iptiq.balancer.RandomLoadBalancerImpl;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        //Simulating access
        var providers = IntStream.range(0, 5).mapToObj(idx -> UUID.randomUUID().toString()).toArray(String[]::new);
        try (var loadBalancer = new RandomLoadBalancerImpl(1)) {
            loadBalancer.register(providers);
            var numberOfInteractions = 10;
            var executor = Executors.newFixedThreadPool(numberOfInteractions);
            for (int i = 0; i < numberOfInteractions; i++) {
                executor.execute(() -> {
                    try {
                        var waitTime = ThreadLocalRandom.current().nextLong(100, 1000);
                        Thread.sleep(waitTime);
                    } catch (InterruptedException e) {
                    }
                    System.out.println(loadBalancer.get());
                });
            }
            executor.shutdown();
        }
    }

}
