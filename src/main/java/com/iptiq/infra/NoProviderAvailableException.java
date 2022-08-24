package com.iptiq.infra;

public class NoProviderAvailableException extends RuntimeException {

    private static final String DEFAULT_MSG = "No providers available at the moment";

    public NoProviderAvailableException() {
        super(DEFAULT_MSG);
    }

}
