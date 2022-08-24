package com.iptiq.infra;

public class LoadBalancerCapacityExceededException extends RuntimeException {

    private static final String DEFAULT_MSG = "The max capacity of the balancer is %d";

    public LoadBalancerCapacityExceededException(final int totalCapacity) {
        super(String.format(DEFAULT_MSG, totalCapacity));
    }

}
