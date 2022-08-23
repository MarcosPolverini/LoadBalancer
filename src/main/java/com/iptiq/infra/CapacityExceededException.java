package com.iptiq.infra;

public class CapacityExceededException extends RuntimeException {

    private static final String defaultMessage = "The max capacity is %d";

    public CapacityExceededException(final int totalCapacity) {
        super(String.format(defaultMessage, totalCapacity));
    }

}
