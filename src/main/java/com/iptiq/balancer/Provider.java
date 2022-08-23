package com.iptiq.balancer;

import java.util.UUID;

public class Provider {

    public String get() {
        return UUID.randomUUID().toString();
    }

}
