package com.iptiq.infra;

public class CapacityExceededException extends RuntimeException {

    private static final String DEFAULT_MSG = "The max capacity is %d";

    public CapacityExceededException(final int totalCapacity) {
        super(String.format(DEFAULT_MSG, totalCapacity));
    }

}
