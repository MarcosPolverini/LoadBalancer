package com.iptiq.infra;

public class ClusterCapacityExceededException extends RuntimeException {

    private static final String DEFAULT_MSG = "Cannot handle the request";

    public ClusterCapacityExceededException() {
        super(DEFAULT_MSG);
    }

}
