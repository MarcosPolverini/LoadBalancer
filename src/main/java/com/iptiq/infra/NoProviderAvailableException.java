package com.iptiq.infra;

public class NoProviderAvailableException extends RuntimeException {

    private static final String DEFAULT_MSG = "No providers registered to the balancer";

    public NoProviderAvailableException() {
        super(DEFAULT_MSG);
    }

}
