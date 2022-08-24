package com.iptiq.infra;

public class ClusterCapacityExceededException extends RuntimeException {

    private static final String DEFAULT_MSG = "Too many requests";

    public ClusterCapacityExceededException() {
        super(DEFAULT_MSG);
    }

}
